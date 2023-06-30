package com.example.todolist.data.local

import com.example.todolist.data.synchronizer.SyncItem
import com.example.todolist.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoLocalDataSource {

    fun observeAll(): Flow<List<TodoItem>>

    suspend fun fetchAll(): List<SyncItem>

    suspend fun fetchBy(id: String): List<TodoItem>

    suspend fun insertOne(todoItem: TodoItem)

    suspend fun replaceAll(newItems: List<TodoItem>)
}