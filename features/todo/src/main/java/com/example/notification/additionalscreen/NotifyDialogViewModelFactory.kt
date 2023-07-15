package com.example.notification.additionalscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.di.TodoFeatureScope
import com.example.settings_api.SettingsRepository
import javax.inject.Inject

@TodoFeatureScope
class NotifyDialogViewModelFactory @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == NotifyDialogViewModel::class.java)
        return NotifyDialogViewModel(settingsRepository) as T
    }
}