package com.example.settings_api.models

data class Settings(
    val theme: Theme,
    val showNotificationDialogOnStart: Boolean,
    val showNotifications: Boolean
)
