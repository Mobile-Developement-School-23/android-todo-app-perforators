package com.example.todo.remote.api.requests

import com.example.todo.remote.api.dto.TodoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoListRequest(
    @SerialName("list") val list: List<TodoDto>
)