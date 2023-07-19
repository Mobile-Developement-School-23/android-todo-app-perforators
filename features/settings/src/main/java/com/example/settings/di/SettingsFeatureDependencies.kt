package com.example.settings.di

import com.example.settings_api.SettingsRepository
import kotlin.properties.Delegates

interface SettingsFeatureDependencies {

    fun settingsRepository(): SettingsRepository
}

interface SettingsFeatureDepsProvider {
    val deps: SettingsFeatureDependencies

    companion object : SettingsFeatureDepsProvider by SettingsFeatureDepsStore
}

object SettingsFeatureDepsStore : SettingsFeatureDepsProvider {

    override var deps: SettingsFeatureDependencies by Delegates.notNull()
}