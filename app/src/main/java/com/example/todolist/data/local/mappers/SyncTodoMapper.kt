package com.example.todolist.data.local.mappers

import com.example.todolist.data.local.database.TodoEntity
import com.example.todolist.data.synchronizer.SyncItem
import javax.inject.Inject

class SyncTodoMapper @Inject constructor(
    private val todoMapper: LocalTodoMapper
) {

    fun map(entities: List<TodoEntity>) = entities.map { map(it) }

    private fun map(entity: TodoEntity): SyncItem {
        return SyncItem(
            item = todoMapper.mapEntity(entity),
            syncStatus = entity.syncStatus
        )
    }
}