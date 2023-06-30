package com.example.todolist.domain

import com.example.todolist.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    fun fetchAll(): Flow<List<TodoItem>>
    fun fetchOne(id: String): Result<TodoItem>
    fun save(item: TodoItem)
    fun remove(item: TodoItem)
}