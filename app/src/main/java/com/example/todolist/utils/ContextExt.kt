package com.example.todolist.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private const val DATA_STORE_NAME = "params"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)