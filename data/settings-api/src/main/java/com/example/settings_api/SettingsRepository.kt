package com.example.settings_api

import com.example.settings_api.models.Settings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun observeSettings(): Flow<Settings>

    suspend fun fetchSettings(): Settings

    suspend fun updateSettings(action: (Settings) -> Settings)
}