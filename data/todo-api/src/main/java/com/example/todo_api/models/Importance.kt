package com.example.todo_api.models

enum class Importance(val id: Int, val value: String) {
    LOW(0, "Низкая"),
    NORMAL(1, "Средняя"),
    HIGH(2, "Высокая")
}

fun importanceFrom(id: Int): Importance {
    for (importance in Importance.values()) {
        if (importance.id == id) return importance
    }
    return Importance.NORMAL
}