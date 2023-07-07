package com.example.todolist.data.repositories.synchronizer

import com.example.todolist.data.local.database.SyncStatus
import com.example.todolist.domain.models.TodoItem

data class SyncItem(val item: TodoItem, val syncStatus: SyncStatus)
