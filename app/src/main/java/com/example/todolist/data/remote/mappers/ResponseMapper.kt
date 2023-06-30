package com.example.todolist.data.remote.mappers

import com.example.todolist.data.remote.RevisionData
import com.example.todolist.data.remote.api.responses.OneItemResponse
import com.example.todolist.data.remote.api.responses.TodoListResponse
import com.example.todolist.domain.models.TodoItem
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