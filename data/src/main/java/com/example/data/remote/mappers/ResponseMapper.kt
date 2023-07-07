package com.example.data.remote.mappers

import com.example.data.remote.RevisionData
import com.example.data.remote.api.responses.OneItemResponse
import com.example.data.remote.api.responses.TodoListResponse
import com.example.domain.models.TodoItem
import javax.inject.Inject

class ResponseMapper @Inject constructor(
    private val todoMapper: RemoteTodoMapper
) {

    fun map(todoListResponse: TodoListResponse): RevisionData<List<TodoItem>> {
        return RevisionData(todoMapper.mapTodoList(todoListResponse.list), todoListResponse.revision)
    }

    fun map(oneItemResponse: OneItemResponse): RevisionData<TodoItem> {
        return RevisionData(todoMapper.map(oneItemResponse.element), oneItemResponse.revision)
    }
}