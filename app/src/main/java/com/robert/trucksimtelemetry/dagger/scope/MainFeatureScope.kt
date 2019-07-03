package com.robert.trucksimtelemetry.dagger.scope

import javax.inject.Scope
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Scope for a main feature module.
 */
@Scope
@Retention(RUNTIME)
annotation class MainFeatureScope
