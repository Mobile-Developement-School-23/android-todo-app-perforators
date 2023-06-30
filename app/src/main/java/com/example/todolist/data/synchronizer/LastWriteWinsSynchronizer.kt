package com.example.todolist.data.synchronizer

import com.example.todolist.data.local.database.SyncStatus
import com.example.todolist.domain.models.TodoItem

class LastWriteWinsSynchronizer : Synchronizer {

    override fun sync(actual: List<TodoItem>, local: List<SyncItem>): List<TodoItem> {
        val result = mutableListOf<TodoItem>()
        val localItemsById = local.associateBy { it.item.id }
        for (item in actual) {
            if (item.id !in localItemsById) continue
            val (localItem, syncStatus) = localItemsById[item.id]!!
            if (syncStatus == SyncStatus.DELETED) continue
            val saved = if (item.changeData.time > localItem.changeData.time) item else localItem
            result.add(saved)
        }
        for (localItem in local) {
            if (localItem.syncStatus == SyncStatus.ADDED) result.add(localItem.item)
        }
        return result
    }
}