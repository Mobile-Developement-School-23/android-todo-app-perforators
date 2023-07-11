package com.example.data.repositories.synchronizer

import com.example.data.local.SyncStatus
import com.example.domain.models.TodoItem

data class SyncItem(val item: TodoItem, val syncStatus: SyncStatus)
