package com.robert.trucksimtelemetry.data.main

import com.robert.trucksimtelemetry.data.Result
import com.robert.trucksimtelemetry.data.main.model.Truck
import java.util.*
import javax.inject.Inject

class TelemetryRepository @Inject constructor(private val remoteDataSource: MainRemoteDataSource) {
    private val cache = mutableMapOf<UUID, Truck>()

    suspend fun loadTruck(): Result<Truck> {
        return remoteDataSource.loadTruck()
    }

    private suspend fun getCommonListData(
        request: suspend () -> Result<List<String>>
    ): Result<List<String>> {
        return request()
    }

    fun getTruck(id: UUID): Result<Truck> {
        val truck = cache[id]
        return if (truck != null) {
            Result.Success(truck)
        } else {
            Result.Error(IllegalStateException("Truck $id not cached"))
        }
    }

    private fun cache(data: List<Truck>) {
        data.associateTo(cache) { UUID.fromString(it.id) to it }
    }
}

