package com.robert.trucksimtelemetry.data.main

import com.robert.trucksimtelemetry.data.Result
import com.robert.trucksimtelemetry.data.api.TelemetryService
import com.robert.trucksimtelemetry.data.main.model.Truck
import com.robert.trucksimtelemetry.util.safeApiCall
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class MainRemoteDataSource @Inject constructor(
    private val service: TelemetryService
) {

    suspend fun loadTruck(): Result<Truck> = safeApiCall(
        call = {

            val response = service.getTruck()
            getResult(response = response, onError = {
                Result.Error(
                    IOException("Error getting brands ${response.code()} ${response.message()}")
                )
            })
        },
        errorMessage = "Error getting truck"
    )

    private inline fun <T : Any> getResult(
        response: Response<T>,
        onError: () -> Result.Error
    ): Result<T> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return Result.Success(body)
            }
        }
        return onError.invoke()
    }
}