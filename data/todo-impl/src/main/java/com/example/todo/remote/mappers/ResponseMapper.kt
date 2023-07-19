package com.example.todo.remote.mappers

import com.example.todo.remote.RevisionData
import com.example.todo.remote.api.responses.OneItemResponse
import com.example.todo.remote.api.responses.TodoListResponse
import com.example.todo_api.models.TodoItem
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