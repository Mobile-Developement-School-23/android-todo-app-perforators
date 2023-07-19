package com.example.settings.di

import com.example.settings.SettingsFragment
import dagger.Component

@SettingsFeatureScope
@Component(
    dependencies = [SettingsFeatureDependencies::class]
)
interface SettingsFeatureComponent {

    fun inject(fragment: SettingsFragment)

    @Component.Factory
    interface Factory {

        fun create(dependencies: SettingsFeatureDependencies): SettingsFeatureComponent
    }
}