package com.robert.trucksimtelemetry.domain

import com.robert.trucksimtelemetry.data.Result
import com.robert.trucksimtelemetry.data.main.TelemetryRepository
import com.robert.trucksimtelemetry.data.main.model.Truck
import javax.inject.Inject

class GetTruckTelemetryUseCase @Inject constructor(private val telemetryRepository: TelemetryRepository) {

    suspend operator fun invoke(): Result<Truck> {
        val result = telemetryRepository.loadTruck()
        return when (result) {
            is Result.Success -> {
                val truck = result.data
                Result.Success(truck)
            }
            is Result.Error -> result
        }
    }

}