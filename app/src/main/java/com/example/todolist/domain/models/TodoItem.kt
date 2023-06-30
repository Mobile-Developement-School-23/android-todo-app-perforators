package com.example.todolist.domain.models

import java.util.Date
import java.util.UUID

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val isDone: Boolean,
    val creationDate: Date,
    val changeData: Date? = null,
    val deadline: Date? = null,
) {
    companion object {
        fun createEmpty() = TodoItem(
            id = UUID.randomUUID().toString(),
            text = "",
            importance = Importance.NORMAL,
            isDone = false,
            creationDate = Date(System.currentTimeMillis())
        )
    }
}