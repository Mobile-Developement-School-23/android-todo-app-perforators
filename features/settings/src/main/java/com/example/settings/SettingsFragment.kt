package com.example.settings

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.navigation.navigateUp
import com.example.settings.databinding.FragmentSettingsBinding
import com.example.settings.di.DaggerSettingsFeatureComponent
import com.example.settings.di.SettingsFeatureDepsStore
import com.example.settings_api.models.Settings
import com.example.settings_api.models.themeFrom
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    internal lateinit var factory: SettingsViewModelFactory

    private val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerSettingsFeatureComponent.factory()
            .create(SettingsFeatureDepsStore.deps)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        binding.cancel.setOnClickListener {
            viewModel.goBack()
        }
        binding.apply.setOnClickListener {
            val theme = themeFrom(binding.pickTheme.selectedItemId.toInt())
            viewModel.updateTheme(theme)
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.settings
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect(::applySettings)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect(::handle)
        }
    }

    private fun applySettings(settings: Settings) {
        binding.pickTheme.setSelection(settings.theme.id)
    }

    private fun handle(event: SettingsViewModel.Event) {
        if (event == SettingsViewModel.Event.GoBack) navigateUp()
    }
}