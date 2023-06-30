package com.example.todolist.domain

import com.example.todolist.domain.models.Items
import com.example.todolist.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {
    fun observeAll(): Flow<Items>

    suspend fun fetchAll()
    suspend fun fetchOne(id: String): Result<TodoItem>
    suspend fun addNew(item: TodoItem): Result<Unit>

    suspend fun edit(item: TodoItem): Result<Unit>
    suspend fun remove(item: TodoItem): Result<Unit>
}