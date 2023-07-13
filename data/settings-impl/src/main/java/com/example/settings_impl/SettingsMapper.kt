package com.example.settings_impl

import com.example.settings_api.models.Settings
import com.example.settings_impl.entities.SettingsEntity
import javax.inject.Inject

class SettingsMapper @Inject constructor() {

    fun map(entity: SettingsEntity) = Settings(theme = entity.theme)
}