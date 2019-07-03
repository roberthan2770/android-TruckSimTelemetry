package com.robert.trucksimtelemetry.data.main

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.robert.trucksimtelemetry.data.Result
import com.robert.trucksimtelemetry.data.api.TelemetryService
import com.robert.trucksimtelemetry.errorResponseBody
import com.robert.trucksimtelemetry.testTruck
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response

class MainRemoteDataSourceTest {

    private val service: TelemetryService = mock()
    private val dataSource = MainRemoteDataSource(service)

    @Test
    fun loadBrands_withSuccess() = runBlocking {
        whenever(service.getTruck()).thenReturn(Response.success(testTruck))

        val result = dataSource.loadTruck()

        assertEquals(Result.Success(testTruck), result)
    }

    @Test
    fun loadBrands_withError() = runBlocking {
        whenever(service.getTruck()).thenReturn(
            Response.error(400, errorResponseBody)
        )

        val result = dataSource.loadTruck()

        assertTrue(result is Result.Error)
    }
}