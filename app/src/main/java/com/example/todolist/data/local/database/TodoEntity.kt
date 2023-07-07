package com.example.todolist.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todolist.domain.models.Importance

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey
    val id: String,
    val text: String,
    val importance: Importance,
    val isDone: Boolean,
    val creationDate: Long,
    val changeData: Long,
    val deadline: Long? = null,
    val lastUpdatedBy: String,
    val syncStatus: SyncStatus
)