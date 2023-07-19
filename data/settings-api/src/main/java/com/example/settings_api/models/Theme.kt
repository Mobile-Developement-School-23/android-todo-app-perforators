package com.example.settings_api.models

enum class Theme(val id: Int) {
    LIGHT(0), BLACK(1), SYSTEM(2)
}

fun themeFrom(id: Int): Theme {
    for (theme in Theme.values()) {
        if (theme.id == id) return theme
    }
    return Theme.SYSTEM
}