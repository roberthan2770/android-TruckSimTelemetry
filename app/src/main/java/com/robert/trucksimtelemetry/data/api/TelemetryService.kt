package com.robert.trucksimtelemetry.data.api

import com.robert.trucksimtelemetry.data.main.model.AccessToken
import com.robert.trucksimtelemetry.data.main.model.Truck
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface TelemetryService {

    @EnvelopePayload("truck")
    @GET("api/ets2/telemetry")
    suspend fun getTruck(): Response<Truck>

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun login(@FieldMap loginParams: Map<String, String>): Response<AccessToken>

    companion object {
        const val ENDPOINT = "http://localhost:25555/"
    }
}
