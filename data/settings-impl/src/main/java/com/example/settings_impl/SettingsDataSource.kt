package com.example.settings_impl

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.settings_impl.entities.SettingsEntity
import com.example.utils.dataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SettingsDataSource @Inject constructor(
    private val context: Context,
    private val json: Json,
    dispatcher: CoroutineDispatcher
) {

    private val settingsKey = stringPreferencesKey(KEY)
    private val scope = CoroutineScope(dispatcher)

    val settings: StateFlow<SettingsEntity> = context.dataStore.data.map { preferences ->
        val stringSettings = preferences[settingsKey]
        if (stringSettings == null) {
            SettingsEntity()
        } else {
            json.decodeFromString(stringSettings)
        }
    }.stateIn(scope, SharingStarted.Eagerly, SettingsEntity())

    suspend fun updateSettings(settings: SettingsEntity) {
        context.dataStore.edit { preferences ->
            preferences[settingsKey] = json.encodeToString(settings)
        }
    }

    companion object {
        private const val KEY = "settings"
    }
}