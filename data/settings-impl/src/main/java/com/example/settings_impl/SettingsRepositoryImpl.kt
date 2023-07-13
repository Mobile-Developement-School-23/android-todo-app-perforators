package com.example.settings_impl

import com.example.settings_api.SettingsRepository
import com.example.settings_api.models.Theme
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val settingsMapper: SettingsMapper,
    private val dispatcher: CoroutineDispatcher
) : SettingsRepository {

    override fun fetchSettings() = settingsDataSource.settings
        .map { settingsMapper.map(it) }

    override suspend fun updateTheme(newTheme: Theme) = withContext(dispatcher) {
        settingsDataSource.updateTheme(newTheme)
    }
}