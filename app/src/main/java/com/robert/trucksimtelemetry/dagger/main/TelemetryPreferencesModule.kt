package com.robert.trucksimtelemetry.dagger.main

import android.content.Context
import com.robert.trucksimtelemetry.dagger.SharedPreferencesModule
import dagger.Module

@Module
class TelemetryPreferencesModule(
    context: Context
) : SharedPreferencesModule(context, "TELEMETRY_PREF")