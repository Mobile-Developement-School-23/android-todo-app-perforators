package com.example.todolist.data.remote.api.responses

import com.example.todolist.data.remote.api.dto.TodoDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OneItemResponse(
    @SerialName("element") val element: TodoDto,
    @SerialName("revision") val revision: Int,
    @SerialName("status") val status: String
)