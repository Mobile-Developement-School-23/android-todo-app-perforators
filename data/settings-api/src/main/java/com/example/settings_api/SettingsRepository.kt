package com.example.settings_api

import com.example.settings_api.models.Settings
import com.example.settings_api.models.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun fetchSettings(): Flow<Settings>

    suspend fun updateTheme(newTheme: Theme)
}