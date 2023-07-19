package com.example.notification.additionalscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.settings_api.SettingsRepository
import kotlinx.coroutines.launch

class NotifyDialogViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    fun notificationOn() {
        viewModelScope.launch {
            repository.updateSettings {
                it.copy(showNotifications = true, showNotificationDialogOnStart = false)
            }
        }
    }

    fun notificationOff() {
        viewModelScope.launch {
            repository.updateSettings {
                it.copy(showNotifications = false, showNotificationDialogOnStart = false)
            }
        }
    }
}