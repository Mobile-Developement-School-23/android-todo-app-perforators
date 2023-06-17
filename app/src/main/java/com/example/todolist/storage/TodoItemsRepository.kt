package com.example.todolist.storage

import com.example.todolist.models.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    fun fetchAll(): Flow<List<TodoItem>>
    fun fetchOne(id: String): Result<TodoItem>
    fun save(item: TodoItem)
    fun remove(item: TodoItem)
}