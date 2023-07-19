package com.example.todo.local.sources

import com.example.todo.synchronizer.SyncItem
import com.example.todo.synchronizer.SyncStatus
import com.example.todo_api.models.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoLocalDataSource {

    fun observeAll(): Flow<List<TodoItem>>

    suspend fun fetchAll(): List<SyncItem>

    suspend fun fetchBy(id: String): List<TodoItem>

    suspend fun delete(id: String)

    suspend fun insertOne(todoItem: TodoItem, syncStatus: SyncStatus)

    suspend fun replaceAll(newItems: List<TodoItem>)
}