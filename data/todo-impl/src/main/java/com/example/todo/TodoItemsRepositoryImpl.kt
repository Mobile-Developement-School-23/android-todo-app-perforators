package com.example.todo

import com.example.todo.synchronizer.SyncStatus
import com.example.todo.local.sources.DeviceIdLocalDataSource
import com.example.todo.local.sources.RevisionLocalDataSource
import com.example.todo.local.sources.TodoLocalDataSource
import com.example.todo.remote.RevisionData
import com.example.todo.remote.TodoRemoteDataSource
import com.example.todo.synchronizer.Synchronizer
import com.example.todo_api.TodoItemsRepository
import com.example.todo_api.models.Items
import com.example.todo_api.models.TodoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val todoRemoteDataSource: TodoRemoteDataSource,
    private val todoLocalDataSource: TodoLocalDataSource,
    private val revisionLocalDataSource: RevisionLocalDataSource,
    deviceIdLocalDataSource: DeviceIdLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val synchronizer: Synchronizer
) : TodoItemsRepository {

    private val areSynchronized = MutableStateFlow(false)
    private val scope = CoroutineScope(dispatcher)

    private val deviceId = deviceIdLocalDataSource.deviceId
        .stateIn(scope, SharingStarted.Eagerly, "")

    private val revision = revisionLocalDataSource.revision
        .stateIn(scope, SharingStarted.Eagerly, -1)

    override fun observeAll() = todoLocalDataSource.observeAll().combine(areSynchronized, ::Items)

    override suspend fun fetchAll() = withErrorHandler {
        todoRemoteDataSource.fetchTodoList()
            .onSuccess {
                val localItems = todoLocalDataSource.fetchAll()
                val newItems = synchronizer.sync(it.data, localItems)
                syncData(newItems)
            }
            .onFailure {
                areSynchronized.value = false
            }
        Result.success(Unit)
    }

    private suspend fun syncData(newItems: List<TodoItem>) {
        todoRemoteDataSource.updateTodoList(newItems, revision.value)
            .onSuccess {
                todoLocalDataSource.replaceAll(it.data)
                revisionLocalDataSource.change(it.revision)
                areSynchronized.value = true
            }.onFailure {
                areSynchronized.value = false
            }
    }

    override suspend fun fetchOne(id: String) = withErrorHandler {
        todoRemoteDataSource.fetchOne(id)
            .onSuccess {
                if (it.revision != revision.value) fetchAll()
            }
        runCatching { todoLocalDataSource.fetchBy(id).first() }
    }

    override suspend fun addNew(item: TodoItem) = withErrorHandler {
        val newItem = item.copy(lastUpdatedBy = deviceId.value)
        changeItem(
            action = {
                todoLocalDataSource.insertOne(newItem, SyncStatus.ADDED)
                todoRemoteDataSource.addNew(revision.value, newItem)
            },
            onSuccess = { todoLocalDataSource.insertOne(it, SyncStatus.SYNCHRONIZED) }
        )
    }

    override suspend fun edit(item: TodoItem) = withErrorHandler {
        val newItem = item.copy(lastUpdatedBy = deviceId.value)
        changeItem(
            action = {
                todoLocalDataSource.insertOne(newItem, SyncStatus.EDITED)
                todoRemoteDataSource.edit(revision.value, newItem)
            },
            onSuccess = { todoLocalDataSource.insertOne(it, SyncStatus.SYNCHRONIZED) }
        )
    }

    override suspend fun remove(item: TodoItem) = withErrorHandler {
        changeItem(
            action = {
                todoLocalDataSource.insertOne(item, SyncStatus.DELETED)
                todoRemoteDataSource.delete(revision.value, item.id)
            },
            onSuccess = { todoLocalDataSource.delete(it.id) }
        )
    }

    private suspend fun <T> withErrorHandler(block: suspend () -> Result<T>) =
        withContext(dispatcher) {
            try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    private suspend fun changeItem(
        action: suspend () -> Result<RevisionData<TodoItem>>,
        onSuccess: suspend (TodoItem) -> Unit
    ): Result<TodoItem> {
        return action()
            .onSuccess {
                onSuccess(it.data)
                revisionLocalDataSource.change(it.revision)
            }
            .map { it.data }
    }
}