package com.example.todo.local.sources

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.utils.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RevisionLocalDataSource @Inject constructor(private val context: Context) {

    private val revisionKey = intPreferencesKey(KEY)

    val revision: Flow<Int> = context.dataStore.data.map { preferences ->
        preferences[revisionKey] ?: INIT_REVISION
    }

    suspend fun change(newValue: Int) {
        context.dataStore.edit { preferences ->
            preferences[revisionKey] = newValue
        }
    }

    companion object {
        private const val KEY = "revision key"
        private const val INIT_REVISION = -1
    }
}
