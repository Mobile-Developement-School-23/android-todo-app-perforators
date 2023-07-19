package com.example.todo.synchronizer

import com.example.todo_api.models.TodoItem
import javax.inject.Inject

class LastWriteWinsSynchronizer @Inject constructor() : Synchronizer {

    override fun sync(actual: List<TodoItem>, local: List<SyncItem>): List<TodoItem> {
        val result = mutableListOf<TodoItem>()
        val localItemsById = local.associateBy { it.item.id }
        for (item in actual) {
            if (item.id in localItemsById) {
                val (localItem, syncStatus) = localItemsById[item.id]!!
                if (syncStatus == SyncStatus.DELETED) continue
                val saved = if (item.changeData.time > localItem.changeData.time) item else localItem
                result.add(saved)
            } else {
                result.add(item)
            }
        }
        for (localItem in local) {
            if (localItem.syncStatus == SyncStatus.ADDED) result.add(localItem.item)
        }
        return result
    }
}