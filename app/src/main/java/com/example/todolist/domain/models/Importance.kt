package com.example.todolist.domain.models

enum class Importance(val id: Int) {
    LOW(0), NORMAL(1), HIGH(2)
}

fun importanceFrom(id: Int): Importance {
    for (importance in Importance.values()) {
        if (importance.id == id) return importance
    }
    return Importance.NORMAL
}