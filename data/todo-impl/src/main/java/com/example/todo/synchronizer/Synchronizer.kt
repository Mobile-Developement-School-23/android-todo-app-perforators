package com.example.todo.synchronizer

import com.example.todo_api.models.TodoItem

interface Synchronizer {
    fun sync(actual: List<TodoItem>, local: List<SyncItem>): List<TodoItem>
}