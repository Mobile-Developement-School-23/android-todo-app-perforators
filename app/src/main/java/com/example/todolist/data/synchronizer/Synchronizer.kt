package com.example.todolist.data.synchronizer

import com.example.todolist.domain.models.TodoItem

interface Synchronizer {
    fun sync(actual: List<TodoItem>, local: List<SyncItem>): List<TodoItem>
}