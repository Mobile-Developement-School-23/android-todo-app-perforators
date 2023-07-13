package com.example.todo.remote

import com.example.todo.remote.api.TodoApi
import com.example.todo.remote.api.requests.SaveNewTodoRequest
import com.example.todo.remote.api.requests.UpdateTodoListRequest
import com.example.todo.remote.mappers.RemoteTodoMapper
import com.example.todo.remote.mappers.ResponseMapper
import com.example.todo_api.models.TodoItem
import kotlinx.coroutines.delay
import javax.inject.Inject

class TodoRemoteDataSourceImpl @Inject constructor(
    private val api: TodoApi,
    private val responseMapper: ResponseMapper,
    private val todoMapper: RemoteTodoMapper
) : TodoRemoteDataSource {

    override suspend fun fetchTodoList() = apiRequest(COMMON_TODO_ERROR) {
        api.fetchTodoList()
    }.map {
        responseMapper.map(it)
    }

    override suspend fun fetchOne(id: String) = apiRequest(COMMON_TODO_ERROR) {
        api.fetchOne(id)
    }.map {
        responseMapper.map(it)
    }

    override suspend fun updateTodoList(
        currentList: List<TodoItem>,
        lastKnownRevision: Int
    ) = apiRequest(COMMON_TODO_ERROR) {
        api.updateList(
            lastKnownRevision,
            UpdateTodoListRequest(
                todoMapper.mapItemList(currentList)
            )
        )
    }.map {
        responseMapper.map(it)
    }

    override suspend fun addNew(revision: Int, item: TodoItem) = apiRequest(ADD_NEW_TODO_ERROR) {
        api.addNewTodo(
            revision,
            SaveNewTodoRequest(todoMapper.map(item))
        )
    }.map {
        responseMapper.map(it)
    }

    override suspend fun edit(revision: Int, item: TodoItem) = apiRequest(EDIT_TODO_ERROR) {
        api.editTodo(
            revision, item.id,
            SaveNewTodoRequest(todoMapper.map(item))
        )
    }.map {
        responseMapper.map(it)
    }

    override suspend fun delete(revision: Int, id: String) = apiRequest(REMOVE_TODO_ERROR) {
        api.delete(revision, id)
    }.map {
        responseMapper.map(it)
    }

    private suspend fun <T> apiRequest(
        errorMessage: String,
        block: suspend () -> T
    ): Result<T> {
        repeat(COUNT_REPEAT) {
            val result = kotlin.runCatching { block() }
            if (result.isSuccess) return result
            delay(REPEAT_DELAY)
        }
        return Result.failure(IllegalStateException(errorMessage))
    }

    companion object {
        private const val COUNT_REPEAT = 3
        private const val REPEAT_DELAY = 300L
        private const val ADD_NEW_TODO_ERROR = "Не удалось добавить новое дело на сервер"
        private const val REMOVE_TODO_ERROR = "Не удалось удалить дело с сервера"
        private const val EDIT_TODO_ERROR = "Не удалось обновить дела на сервере"
        private const val COMMON_TODO_ERROR = "Произошла ошибка при загрузке/получении данных"
    }
}
