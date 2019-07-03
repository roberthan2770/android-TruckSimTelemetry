package com.robert.trucksimtelemetry.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.robert.trucksimtelemetry.data.Result
import com.robert.trucksimtelemetry.data.main.TelemetryRepository
import com.robert.trucksimtelemetry.testTruck
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class GetTruckTelemetryUseCaseTest {
    private val repository: TelemetryRepository = mock()
    private val getTruckTelemetryUseCase = GetTruckTelemetryUseCase(repository)

    @Test
    fun getBrands_withSuccess() = runBlocking {
        val expected = Result.Success(testTruck)
        whenever(repository.loadTruck()).thenReturn(
            expected
        )

        val result = getTruckTelemetryUseCase()

        assertEquals(Result.Success(testTruck), result)
    }

    @Test
    fun getBrands_withError() = runBlocking {
        val expected = Result.Error(IOException("Failed"))
        whenever(repository.loadTruck()).thenReturn(expected)

        val result = getTruckTelemetryUseCase()

        assertEquals(expected, result)
    }
}