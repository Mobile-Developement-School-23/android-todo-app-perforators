package com.example.todolist.domain.models

import com.example.todolist.utils.convertToDate
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
    val lastUpdatedBy: String
) {
    companion object {
        fun createEmpty(deviceId: String) = TodoItem(
            id = UUID.randomUUID().toString(),
            text = "",
            importance = Importance.NORMAL,
            isDone = false,
            creationDate = Date(System.currentTimeMillis()),
            lastUpdatedBy = deviceId,
            changeData = System.currentTimeMillis().convertToDate()
        )
    }
}