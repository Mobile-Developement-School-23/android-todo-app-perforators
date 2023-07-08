package com.example.data.local.sources

import com.example.data.local.SyncStatus
import com.example.data.local.TodoDao
import com.example.data.local.mappers.LocalTodoMapper
import com.example.data.local.mappers.SyncTodoMapper
import com.example.data.repositories.synchronizer.SyncItem
import com.example.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoLocalDataSourceImpl @Inject constructor(
    private val todoDao: TodoDao,
    private val syncTodoMapper: SyncTodoMapper,
    private val todoMapper: LocalTodoMapper
) : TodoLocalDataSource {

    override fun observeAll(): Flow<List<TodoItem>> {
        return todoMapper.mapEntitiesFlow(
            todoDao.observeAll().map { entities -> entities.filter { it.syncStatus != SyncStatus.DELETED } }
        )
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