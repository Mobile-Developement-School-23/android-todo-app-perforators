package com.example.todolist.models

import java.util.Date

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
        fun empty() = TodoItem(
            id = "${System.currentTimeMillis()}",
            text = "",
            importance = Importance.NORMAL,
            isDone = false,
            creationDate = Date(System.currentTimeMillis())
        )
    }
}