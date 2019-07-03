package com.robert.trucksimtelemetry.dagger.main

import com.robert.trucksimtelemetry.MainActivity
import com.robert.trucksimtelemetry.coreComponent

fun inject(activity: MainActivity) {
    DaggerMainComponent.builder()
        .coreComponent(activity.coreComponent())
        .sharedPreferencesModule(TelemetryPreferencesModule(activity))
        .build()
        .inject(activity)

}