package com.example.data.local.mappers

import com.example.commom.convertToDate
import com.example.data.local.SyncStatus
import com.example.data.local.TodoEntity
import com.example.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalTodoMapper @Inject constructor() {

    fun mapEntitiesFlow(entities: Flow<List<TodoEntity>>) = entities.map { mapEntities(it) }

    fun mapItems(items: List<TodoItem>) = items.map { mapItem(it) }

    fun mapEntity(entity: TodoEntity) = TodoItem(
        id = entity.id,
        text = entity.text,
        importance = entity.importance,
        isDone = entity.isDone,
        creationDate = entity.creationDate.convertToDate(),
        changeData = entity.changeData.convertToDate(),
        deadline = entity.deadline?.convertToDate(),
        lastUpdatedBy = entity.lastUpdatedBy
    )

    fun mapEntities(entities: List<TodoEntity>) = entities.map { mapEntity(it) }

    fun mapItem(item: TodoItem, syncStatus: SyncStatus = SyncStatus.SYNCHRONIZED) =
        TodoEntity(
            id = item.id,
            text = item.text,
            importance = item.importance,
            isDone = item.isDone,
            creationDate = item.creationDate.time,
            changeData = item.changeData.time,
            deadline = item.deadline?.time,
            lastUpdatedBy = item.lastUpdatedBy,
            syncStatus = syncStatus
        )
}