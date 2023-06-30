package com.example.todolist.data.local

import com.example.todolist.data.local.database.TodoDao
import com.example.todolist.data.local.mappers.SyncTodoMapper
import com.example.todolist.data.local.mappers.TodoMapper
import com.example.todolist.data.synchronizer.SyncItem
import com.example.todolist.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow

class TodoLocalDataSourceImpl(
    private val todoDao: TodoDao,
    private val syncTodoMapper: SyncTodoMapper,
    private val todoMapper: TodoMapper
) : TodoLocalDataSource {

    override fun observeAll(): Flow<List<TodoItem>> {
        return todoMapper.map(todoDao.observeAll())
    }

    override suspend fun fetchAll(): List<SyncItem> {
        return syncTodoMapper.map(todoDao.fetchAll())
    }

    override suspend fun replaceAll(newItems: List<TodoItem>) {
        todoDao.replaceAll(todoMapper.map(newItems))
    }

    override suspend fun fetchBy(id: String): List<TodoItem> {
        return todoMapper.map(todoDao.fetchBy(id))
    }

    override suspend fun insertOne(todoItem: TodoItem) {
        todoDao.insertOne(todoMapper.map(todoItem))
    }
}