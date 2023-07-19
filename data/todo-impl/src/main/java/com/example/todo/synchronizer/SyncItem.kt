package com.example.todo.synchronizer

import com.example.todo_api.models.TodoItem

data class SyncItem(val item: TodoItem, val syncStatus: SyncStatus)
