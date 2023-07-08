package com.example.domain

import com.example.domain.models.Items
import com.example.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {

    fun observeAll(): Flow<Items>

    suspend fun fetchAll(): Result<Unit>

    suspend fun fetchOne(id: String): Result<TodoItem>

    suspend fun addNew(item: TodoItem): Result<TodoItem>

    suspend fun edit(item: TodoItem): Result<TodoItem>

    suspend fun remove(item: TodoItem): Result<TodoItem>
}