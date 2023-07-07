package com.example.todolist.data.remote.api.responses

import com.example.todolist.data.remote.api.dto.TodoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoListResponse(
    @SerialName("status") val status: String,
    @SerialName("list") val list: List<TodoDto>,
    @SerialName("revision") val revision: Int
)