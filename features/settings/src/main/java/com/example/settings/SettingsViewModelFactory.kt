package com.example.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.settings.di.SettingsFeatureScope
import com.example.settings_api.SettingsRepository
import javax.inject.Inject

@SettingsFeatureScope
class SettingsViewModelFactory @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == SettingsViewModel::class.java)
        return SettingsViewModel(settingsRepository) as T
    }
}