package com.robert.trucksimtelemetry.dagger.main

import android.content.SharedPreferences
import com.google.gson.Gson
import com.robert.trucksimtelemetry.BuildConfig
import com.robert.trucksimtelemetry.dagger.SharedPreferencesModule
import com.robert.trucksimtelemetry.dagger.TelemetryApi
import com.robert.trucksimtelemetry.dagger.scope.MainFeatureScope
import com.robert.trucksimtelemetry.data.api.ClientAuthInterceptor
import com.robert.trucksimtelemetry.data.api.DeEnvelopingConverter
import com.robert.trucksimtelemetry.data.api.TelemetryService
import com.robert.trucksimtelemetry.data.main.AuthTokenLocalDataSource
import com.robert.trucksimtelemetry.data.main.MainRemoteDataSource
import com.robert.trucksimtelemetry.data.main.TelemetryRepository
import dagger.Lazy
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class TelemetryDataModule {

    @Provides
    @MainFeatureScope
    fun provideAuthTokenLocalDataSource(
        sharedPreferences: SharedPreferences
    ): AuthTokenLocalDataSource =
        AuthTokenLocalDataSource.getInstance(sharedPreferences)

    @Provides
    @TelemetryApi
    fun providePrivateOkHttpClient(
        upstream: OkHttpClient,
        tokenHolder: AuthTokenLocalDataSource
    ): OkHttpClient {
        return upstream.newBuilder()
            .connectTimeout(1200L, TimeUnit.MILLISECONDS)
            .addInterceptor(ClientAuthInterceptor(tokenHolder, BuildConfig.CLIENT_ID))
            .build()
    }

    @Provides
    @MainFeatureScope
    fun provideTelemetryService(
        @TelemetryApi client: Lazy<OkHttpClient>,
        gson: Gson,
        gsonConverterFactory: GsonConverterFactory,
        sharedPreferences: SharedPreferences
    ): TelemetryService {
        val serverIP = sharedPreferences.getString(SharedPreferencesModule.KEY_SERVER_IP, "")!!
        val endpoint =
            if (serverIP.isEmpty()) TelemetryService.ENDPOINT else "http://$serverIP:25555/"
        return Retrofit.Builder()
            .baseUrl(endpoint)
            .callFactory { client.get().newCall(it) }
            .addConverterFactory(DeEnvelopingConverter(gson))
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(TelemetryService::class.java)
    }

    @Provides
    @MainFeatureScope
    fun provideTelemetryRepository(
        mainRemoteDataSource: MainRemoteDataSource
    ): TelemetryRepository = TelemetryRepository(mainRemoteDataSource)

    @Provides
    @MainFeatureScope
    fun provideTelemetryRemoteDataSource(service: TelemetryService): MainRemoteDataSource =
        MainRemoteDataSource(service)
}