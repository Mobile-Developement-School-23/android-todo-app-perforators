package com.example.data.local.sources

import com.example.data.local.SyncStatus
import com.example.data.repositories.synchronizer.SyncItem
import com.example.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoLocalDataSource {

    fun observeAll(): Flow<List<TodoItem>>

    suspend fun fetchAll(): List<SyncItem>

    suspend fun fetchBy(id: String): List<TodoItem>

    suspend fun delete(id: String)

    suspend fun insertOne(todoItem: TodoItem, syncStatus: SyncStatus)

    suspend fun replaceAll(newItems: List<TodoItem>)
}