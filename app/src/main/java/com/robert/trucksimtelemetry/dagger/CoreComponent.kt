package com.robert.trucksimtelemetry.dagger

import com.google.gson.Gson
import dagger.Component
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Component providing application wide singletons.
 * To call this make use of PlaidApplication.coreComponent or the
 * Activity.coreComponent extension function.
 */
@Component(modules = [CoreDataModule::class])
@Singleton
interface CoreComponent {

    @Component.Builder
    interface Builder {
        fun build(): CoreComponent
    }

    fun provideOkHttpClient(): OkHttpClient
    fun provideGson(): Gson
    fun provideGsonConverterFactory(): GsonConverterFactory
}
