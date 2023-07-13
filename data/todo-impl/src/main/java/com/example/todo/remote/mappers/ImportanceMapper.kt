package com.example.todo.remote.mappers

import com.example.todo_api.models.Importance
import javax.inject.Inject

class ImportanceMapper @Inject constructor() {

    fun map(importance: String): Importance {
        return when (importance) {
            LOW -> Importance.LOW
            IMPORTANT -> Importance.HIGH
            else -> Importance.NORMAL
        }
    }

    fun map(importance: Importance): String {
        return when (importance) {
            Importance.LOW -> LOW
            Importance.HIGH -> IMPORTANT
            else -> BASIC
        }
    }

    companion object {
        private const val LOW = "low"
        private const val IMPORTANT = "important"
        private const val BASIC = "basic"
    }
}