package com.example.settings_impl

import com.example.settings_api.SettingsRepository
import com.example.settings_api.models.Settings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val settingsMapper: SettingsMapper,
    private val dispatcher: CoroutineDispatcher
) : SettingsRepository {

    override fun observeSettings() = settingsDataSource.settings
        .map { settingsMapper.map(it) }

    override suspend fun fetchSettings(): Settings {
        return settingsMapper.map(settingsDataSource.settings.value)
    }

    override suspend fun updateSettings(action: (Settings) -> Settings) = withContext(dispatcher) {
        val oldSettings = settingsMapper.map(settingsDataSource.settings.value)
        val newSettings = settingsMapper.map(action(oldSettings))
        settingsDataSource.updateSettings(newSettings)
    }
}