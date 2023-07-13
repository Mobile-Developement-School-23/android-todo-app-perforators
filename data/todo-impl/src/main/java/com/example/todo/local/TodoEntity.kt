package com.example.todo.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo.synchronizer.SyncStatus

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey
    val id: String,
    val text: String,
    val importance: com.example.todo_api.models.Importance,
    val isDone: Boolean,
    val creationDate: Long,
    val changeData: Long,
    val deadline: Long? = null,
    val lastUpdatedBy: String,
    val syncStatus: SyncStatus
)