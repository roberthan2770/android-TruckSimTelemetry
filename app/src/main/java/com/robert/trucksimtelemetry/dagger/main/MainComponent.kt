package com.robert.trucksimtelemetry.dagger.main

import com.robert.trucksimtelemetry.MainActivity
import com.robert.trucksimtelemetry.dagger.BaseActivityComponent
import com.robert.trucksimtelemetry.dagger.CoreComponent
import com.robert.trucksimtelemetry.dagger.SharedPreferencesModule
import com.robert.trucksimtelemetry.dagger.scope.MainFeatureScope
import dagger.Component

/**
 * Dagger component for [MainActivity]
 */
@Component(
    modules = [
        TelemetryDataModule::class,
        SharedPreferencesModule::class
    ],
    dependencies = [CoreComponent::class]
)
@MainFeatureScope
interface MainComponent : BaseActivityComponent<MainActivity> {

    interface Builder {
        @Component.Builder
        interface Builder {
            fun build(): MainComponent
            fun coreComponent(component: CoreComponent): Builder
            fun sharedPreferencesModule(module: SharedPreferencesModule): Builder
        }
    }
}
