package com.example.todolist.data.local.sources

import com.example.todolist.data.local.database.SyncStatus
import com.example.todolist.data.local.database.TodoDao
import com.example.todolist.data.local.mappers.SyncTodoMapper
import com.example.todolist.data.local.mappers.LocalTodoMapper
import com.example.todolist.data.synchronizer.SyncItem
import com.example.todolist.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoLocalDataSourceImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val syncTodoMapper: SyncTodoMapper,
    private val todoMapper: LocalTodoMapper
) : TodoLocalDataSource {

    override fun observeAll(): Flow<List<TodoItem>> {
        return todoMapper.mapEntitiesFlow(todoDao.observeAll())
    }

    override suspend fun fetchAll(): List<SyncItem> {
        return syncTodoMapper.map(todoDao.fetchAll())
    }

    override suspend fun replaceAll(newItems: List<TodoItem>) {
        todoDao.replaceAll(todoMapper.mapItems(newItems))
    }

    override suspend fun delete(id: String) {
        todoDao.deleteBy(id)
    }

    override suspend fun fetchBy(id: String): List<TodoItem> {
        return todoMapper.mapEntities(todoDao.fetchBy(id))
    }

    override suspend fun insertOne(todoItem: TodoItem, syncStatus: SyncStatus) {
        todoDao.insertOne(todoMapper.mapItem(todoItem, syncStatus))
    }
}