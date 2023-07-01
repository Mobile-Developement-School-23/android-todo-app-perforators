package com.example.todolist.data.repositories

import com.example.todolist.data.connectivityobserver.ConnectivityObserver
import com.example.todolist.data.local.database.SyncStatus
import com.example.todolist.data.local.sources.DeviceIdLocalDataSource
import com.example.todolist.data.local.sources.RevisionLocalDataSource
import com.example.todolist.data.local.sources.TodoLocalDataSource
import com.example.todolist.data.remote.NetworkResult
import com.example.todolist.data.remote.RevisionData
import com.example.todolist.data.remote.TodoRemoteDataSource
import com.example.todolist.data.remote.onError
import com.example.todolist.data.remote.onSuccess
import com.example.todolist.data.remote.repeatOnError
import com.example.todolist.data.remote.toResult
import com.example.todolist.data.repositories.synchronizer.Synchronizer
import com.example.todolist.domain.TodoItemsRepository
import com.example.todolist.domain.models.Items
import com.example.todolist.domain.models.TodoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoItemsRepositoryImpl @Inject constructor(
    private val todoRemoteDataSource: TodoRemoteDataSource,
    private val todoLocalDataSource: TodoLocalDataSource,
    private val revisionLocalDataSource: RevisionLocalDataSource,
    deviceIdLocalDataSource: DeviceIdLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val synchronizer: Synchronizer,
    private val connectivityObserver: ConnectivityObserver
) : TodoItemsRepository {

    private val areSynchronized = MutableStateFlow(false)
    private val scope = CoroutineScope(dispatcher)

    private val deviceId = deviceIdLocalDataSource.deviceId
        .stateIn(scope, SharingStarted.Eagerly, "")

    private val revision = revisionLocalDataSource.revision
        .stateIn(scope, SharingStarted.Eagerly, -1)

    init {
        scope.launch {
            connectivityObserver.observe().collect {
                if (it == ConnectivityObserver.Status.Available) fetchAll()
            }
        }
    }

    override fun observeAll() = todoLocalDataSource.observeAll().combine(areSynchronized, ::Items)

    override suspend fun fetchAll() = withErrorHandler {
        repeatOnError(COUNT_REPEAT, REPEAT_DELAY, todoRemoteDataSource::fetchTodoList)
            .onSuccess {
                val localItems = todoLocalDataSource.fetchAll()
                val newItems = synchronizer.sync(it.data, localItems)
                syncData(newItems)
            }
            .onError {
                areSynchronized.value = false
            }
        Result.success(Unit)
    }

    private suspend fun syncData(newItems: List<TodoItem>) {
        repeatOnError(COUNT_REPEAT, REPEAT_DELAY) {
            todoRemoteDataSource.updateTodoList(newItems, revision.value)
        }.onSuccess {
            todoLocalDataSource.replaceAll(it.data)
            revisionLocalDataSource.change(it.revision)
            areSynchronized.value = true
        }.onError {
            areSynchronized.value = false
        }
    }

    override suspend fun fetchOne(id: String): Result<TodoItem> {
        try {
            repeatOnError(COUNT_REPEAT, REPEAT_DELAY) {
                todoRemoteDataSource.fetchOne(id)
            }.onSuccess {
                if (it.revision != revision.value) fetchAll()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return runCatching { todoLocalDataSource.fetchBy(id).first() }
    }

    override suspend fun addNew(item: TodoItem) = withErrorHandler {
        val newItem = item.copy(lastUpdatedBy = deviceId.value)
        changeItem(
            onChange = {
                todoLocalDataSource.insertOne(newItem, SyncStatus.ADDED)
                todoRemoteDataSource.addNew(revision.value, newItem)
            },
            onSuccess = { todoLocalDataSource.insertOne(newItem, SyncStatus.SYNCHRONIZED) },
            errorMessage = ADD_NEW_TODO_ERROR
        )
    }

    override suspend fun edit(item: TodoItem) = withErrorHandler {
        val newItem = item.copy(lastUpdatedBy = deviceId.value)
        changeItem(
            onChange = {
                todoLocalDataSource.insertOne(newItem, SyncStatus.EDITED)
                todoRemoteDataSource.edit(revision.value, newItem)
            },
            onSuccess = { todoLocalDataSource.insertOne(newItem, SyncStatus.SYNCHRONIZED) },
            errorMessage = EDIT_TODO_ERROR
        )
    }

    override suspend fun remove(item: TodoItem) = withErrorHandler {
        changeItem(
            onChange = {
                todoLocalDataSource.insertOne(item, SyncStatus.DELETED)
                todoRemoteDataSource.delete(revision.value, item.id)
            },
            onSuccess = { todoLocalDataSource.delete(item.id) },
            errorMessage = REMOVE_TODO_ERROR
        )
    }

    private suspend fun <T> withErrorHandler(block: suspend () -> Result<T>) =
        withContext(dispatcher) {
            try {
                block()
            } catch (e: Exception) {
                Result.failure(IllegalStateException(COMMON_TODO_ERROR))
            }
        }

    private suspend fun changeItem(
        onChange: suspend () -> NetworkResult<RevisionData<TodoItem>>,
        onSuccess: suspend () -> Unit,
        errorMessage: String
    ): Result<TodoItem> {
        return repeatOnError(COUNT_REPEAT, REPEAT_DELAY, onChange)
            .onSuccess {
                onSuccess()
                revisionLocalDataSource.change(it.revision)
            }
            .toResult(errorMessage)
            .map { it.data }
    }

    companion object {
        private const val COUNT_REPEAT = 3
        private const val REPEAT_DELAY = 500L
        private const val ADD_NEW_TODO_ERROR = "Не удалось добавить новое дело на сервер"
        private const val REMOVE_TODO_ERROR = "Не удалось удалить дело с сервера"
        private const val EDIT_TODO_ERROR = "Не удалось обновить дела на сервере"
        private const val COMMON_TODO_ERROR = "Произошла ошибка при загрузке/получении данных"
    }
}