package com.example.settings_impl.entities

import com.example.settings_api.models.Theme
import kotlinx.serialization.Serializable

@Serializable
data class SettingsEntity(
    val theme: Theme = Theme.SYSTEM,
    val showNotificationDialog: Boolean = true,
    val showNotifications: Boolean = false
)
