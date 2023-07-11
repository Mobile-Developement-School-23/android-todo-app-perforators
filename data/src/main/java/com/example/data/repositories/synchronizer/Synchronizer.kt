package com.example.data.repositories.synchronizer

import com.example.domain.models.TodoItem

interface Synchronizer {
    fun sync(actual: List<TodoItem>, local: List<SyncItem>): List<TodoItem>
}