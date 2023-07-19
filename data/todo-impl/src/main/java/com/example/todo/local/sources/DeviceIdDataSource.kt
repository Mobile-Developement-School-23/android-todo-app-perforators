package com.example.todo.local.sources

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.utils.dataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class DeviceIdDataSource @Inject constructor(
    dispatcher: CoroutineDispatcher,
    context: Context
) {

    private val key = stringPreferencesKey(KEY)
    private val scope = CoroutineScope(dispatcher)

    init {
        scope.launch {
            context.dataStore.edit { preferences ->
                val oldValue = preferences[key]
                if (oldValue == null) {
                    preferences[key] = generateDeviceId()
                }
            }
        }
    }

    val deviceId = context.dataStore.data.map { preferences ->
        preferences[key] ?: ""
    }.stateIn(scope, SharingStarted.Eagerly, "")

    private fun generateDeviceId() = UUID.randomUUID().toString()

    companion object {
        private const val KEY = "deviceId"
    }
}