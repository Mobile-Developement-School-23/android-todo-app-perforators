package com.example.todolist.data.remote.api.requests

import com.example.todolist.data.remote.api.dto.TodoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateTodoListRequest(
    @SerialName("element") val element: TodoDto
)