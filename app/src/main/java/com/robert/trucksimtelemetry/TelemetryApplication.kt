package com.robert.trucksimtelemetry

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.BuildCompat
import com.robert.trucksimtelemetry.dagger.CoreComponent
import com.robert.trucksimtelemetry.dagger.DaggerCoreComponent

class TelemetryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val nightMode = if (BuildCompat.isAtLeastQ()) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        } else {
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.create()
    }

    companion object {
        @JvmStatic
        fun coreComponent(context: Context) =
            (context.applicationContext as TelemetryApplication).coreComponent
    }
}

fun Activity.coreComponent() = TelemetryApplication.coreComponent(this)