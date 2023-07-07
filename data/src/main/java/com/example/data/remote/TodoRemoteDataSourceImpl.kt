package com.example.data.remote

import com.example.data.remote.api.TodoApi
import com.example.data.remote.api.requests.SaveNewTodoRequest
import com.example.data.remote.api.requests.UpdateTodoListRequest
import com.example.data.remote.mappers.RemoteTodoMapper
import com.example.data.remote.mappers.ResponseMapper
import com.example.domain.models.TodoItem
import javax.inject.Inject

class TodoRemoteDataSourceImpl @Inject constructor(
    private val api: TodoApi,
    private val responseMapper: ResponseMapper,
    private val todoMapper: RemoteTodoMapper
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
        api.updateList(
            lastKnownRevision,
            UpdateTodoListRequest(
                todoMapper.mapItemList(currentList)
            )
        )
    }.mapSuccess {
        responseMapper.map(it)
    }

    override suspend fun addNew(revision: Int, item: TodoItem) = doNetworkAction {
        api.addNewTodo(
            revision,
            SaveNewTodoRequest(todoMapper.map(item))
        )
    }.mapSuccess {
        responseMapper.map(it)
    }

    override suspend fun edit(revision: Int, item: TodoItem) = doNetworkAction {
        api.editTodo(
            revision, item.id,
            SaveNewTodoRequest(todoMapper.map(item))
        )
    }.mapSuccess {
        responseMapper.map(it)
    }

    override suspend fun delete(revision: Int, id: String) = doNetworkAction {
        api.delete(revision, id)
    }.mapSuccess {
        responseMapper.map(it)
    }
}
