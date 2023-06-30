package com.example.todolist.data.local.mappers

import com.example.todolist.data.local.database.TodoEntity
import com.example.todolist.data.synchronizer.SyncItem

class SyncTodoMapper(
    private val todoMapper: TodoMapper
) {

    fun map(entities: List<TodoEntity>) = entities.map { map(it) }

    private fun map(entity: TodoEntity): SyncItem {
        return SyncItem(
            item = todoMapper.map(entity),
            syncStatus = entity.syncStatus
        )
    }
}