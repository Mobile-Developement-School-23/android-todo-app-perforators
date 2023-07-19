package com.example.todo_api.models

import com.example.commom.convertToDate
import java.util.Date
import java.util.UUID

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val isDone: Boolean,
    val creationDate: Date,
    val changeData: Date,
    val deadline: Date? = null,
    val lastUpdatedBy: String = ""
) {

    companion object {
        fun createEmpty() = TodoItem(
            id = UUID.randomUUID().toString(),
            text = "",
            importance = Importance.NORMAL,
            isDone = false,
            creationDate = Date(System.currentTimeMillis()),
            changeData = System.currentTimeMillis().convertToDate()
        )
    }
}