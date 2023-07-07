package com.example.data.remote.api.responses

import com.example.data.remote.api.dto.TodoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TodoListResponse(
    @SerialName("status") val status: String,
    @SerialName("list") val list: List<TodoDto>,
    @SerialName("revision") val revision: Int
)