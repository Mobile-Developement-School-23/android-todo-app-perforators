package com.example.data.local.sources

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.utils.dataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

class DeviceIdLocalDataSource @Inject constructor(
    dispatcher: CoroutineDispatcher,
    context: Context
) {

    private val deviceIdKey = stringPreferencesKey(KEY)
    private val scope = CoroutineScope(dispatcher)

    init {
        scope.launch {
            context.dataStore.edit { preferences ->
                val oldValue = preferences[deviceIdKey]
                if (oldValue == null) {
                    preferences[deviceIdKey] = generateDeviceId()
                }
            }
        }
    }

    val deviceId: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[deviceIdKey] ?: ""
    }

    private fun generateDeviceId() = UUID.randomUUID().toString()

    companion object {
        private const val KEY = "deviceId key"
    }
}