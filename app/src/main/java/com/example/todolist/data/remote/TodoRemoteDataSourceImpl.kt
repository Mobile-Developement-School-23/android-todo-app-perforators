package com.example.todolist.data.remote

import com.example.todolist.data.remote.api.TodoApi
import com.example.todolist.data.remote.api.requests.UpdateTodoListRequest
import com.example.todolist.data.remote.mappers.ResponseMapper
import com.example.todolist.data.remote.mappers.TodoMapper
import com.example.todolist.domain.models.TodoItem

class TodoRemoteDataSourceImpl(
    private val api: TodoApi,
    private val responseMapper: ResponseMapper,
    private val todoMapper: TodoMapper
) : TodoRemoteDataSource {

    override suspend fun fetchTodoList() = doNetworkAction {
        api.fetchTodoList()
    }.mapSuccess {
        responseMapper.map(it)
    }

    override suspend fun fetchOne(id: String) = doNetworkAction {
        api.fetchOne(id)
    }.mapSuccess {
        responseMapper.map(it)
    }

    override suspend fun updateTodoList(
        currentList: List<TodoItem>,
        lastKnownRevision: Int
    ) = doNetworkAction {
        api.updateList(lastKnownRevision, UpdateTodoListRequest(todoMapper.map(currentList)))
    }.mapSuccess {
        responseMapper.map(it)
    }
}
