package com.example.todolist.data

import com.example.todolist.data.local.TodoLocalDataSource
import com.example.todolist.data.remote.TodoRemoteDataSource
import com.example.todolist.data.remote.onError
import com.example.todolist.data.remote.onSuccess
import com.example.todolist.data.remote.repeatOnError
import com.example.todolist.data.synchronizer.Synchronizer
import com.example.todolist.domain.TodoItemsRepository
import com.example.todolist.domain.models.Items
import com.example.todolist.domain.models.TodoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class TodoItemsRepositoryImpl(
    private val todoRemoteDataSource: TodoRemoteDataSource,
    private val todoLocalDataSource: TodoLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
    private val synchronizer: Synchronizer
) : TodoItemsRepository {

    private val areSynchronized = MutableStateFlow(false)
    private var currentRevision = 0

    override fun observeAll() = todoLocalDataSource.observeAll().combine(areSynchronized, ::Items)

    override suspend fun fetchAll(): Unit = withContext(dispatcher) {
        repeatOnError(COUNT_REPEAT, REPEAT_DELAY, todoRemoteDataSource::fetchTodoList)
            .onSuccess {
                val localItems = todoLocalDataSource.fetchAll()
                val newItems = synchronizer.sync(it.data, localItems)
                syncData(newItems)
            }
            .onError {
                areSynchronized.value = false
            }
    }

    private suspend fun syncData(newItems: List<TodoItem>) {
        repeatOnError(COUNT_REPEAT, REPEAT_DELAY) {
            todoRemoteDataSource.updateTodoList(newItems, 0)
        }.onSuccess {
            todoLocalDataSource.replaceAll(it.data)
            currentRevision = it.revision
            areSynchronized.value = true
        }.onError {
            areSynchronized.value = false
        }
    }

    override suspend fun fetchOne(id: String) = withContext(dispatcher) {
        repeatOnError(COUNT_REPEAT, REPEAT_DELAY) {
            todoRemoteDataSource.fetchOne(id)
        }.onSuccess {
            if (it.revision != currentRevision) fetchAll()
        }
        runCatching { todoLocalDataSource.fetchBy(id).first() }
    }

    override suspend fun addNew(item: TodoItem): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun edit(item: TodoItem): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun remove(item: TodoItem): Result<Unit> {
        TODO("Not yet implemented")
    }

    companion object {
        private const val COUNT_REPEAT = 3
        private const val REPEAT_DELAY = 300L
    }
}