package com.robert.trucksimtelemetry.dagger

import android.content.Context
import android.content.SharedPreferences
import com.robert.trucksimtelemetry.dagger.scope.MainFeatureScope
import dagger.Module
import dagger.Provides

/**
 * Provide [SharedPreferences] to this app's components.
 */
@Module
open class SharedPreferencesModule(val context: Context, val name: String) {

    @Provides
    @MainFeatureScope
    fun provideSharedPreferences(): SharedPreferences {
        return context.applicationContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    companion object {
        const val KEY_SERVER_IP = "KEY_SERVER_IP"
    }
}
