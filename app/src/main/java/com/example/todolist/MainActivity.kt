package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.settings_api.SettingsRepository
import com.example.settings_api.models.Theme
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    internal lateinit var repository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.app.R.layout.activity_main)
        (application as App).applicationComponent.inject(this)
        observeTheme()
    }

    private fun observeTheme() {
        lifecycleScope.launch {
            repository.fetchSettings()
                .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
                .collect {
                    applyTheme(it.theme)
                }
        }
    }

    private fun applyTheme(newTheme: Theme) {
        when (newTheme) {
            Theme.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.BLACK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
