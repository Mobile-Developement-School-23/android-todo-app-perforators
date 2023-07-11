package com.example.data.local.sources

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.utils.dataStore
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class TokenLocalDataSource @Inject constructor(
    private val context: Context,
    dispatcher: CoroutineDispatcher
) {

    private val tokenKey = stringPreferencesKey(KEY)
    private val scope = CoroutineScope(dispatcher)

    val token: StateFlow<String> = context.dataStore.data.map { preferences ->
        preferences[tokenKey] ?: DEFAULT_TOKEN
    }.stateIn(scope, SharingStarted.Eagerly, DEFAULT_TOKEN)

    suspend fun change(newToken: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = newToken
        }
    }

    companion object {
        private const val KEY = "token key"
        const val DEFAULT_TOKEN = "Bearer overshelving"
    }
}