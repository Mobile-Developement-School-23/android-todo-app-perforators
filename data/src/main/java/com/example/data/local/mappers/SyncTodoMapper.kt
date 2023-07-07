package com.example.data.local.mappers

import com.example.data.local.TodoEntity
import com.example.data.repositories.synchronizer.SyncItem
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