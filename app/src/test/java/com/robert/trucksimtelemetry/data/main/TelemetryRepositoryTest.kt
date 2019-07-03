package com.robert.trucksimtelemetry.data.main

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.robert.trucksimtelemetry.data.Result
import com.robert.trucksimtelemetry.testTruck
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException


class TelemetryRepositoryTest {
    private val dataSource: MainRemoteDataSource = mock()
    private val repository = TelemetryRepository(dataSource)


    @Test
    fun loadBrands_withSuccess() = runBlocking {
        whenever(dataSource.loadTruck()).thenReturn(Result.Success(testTruck))

        val data = repository.loadTruck()

        assertEquals(Result.Success(testTruck), data)
    }

    @Test
    fun loadBrands_withError() = runBlocking {
        whenever(dataSource.loadTruck()).thenReturn(Result.Error(IOException("Failed")))

        val data = repository.loadTruck()

        assertTrue(data is Result.Error)
    }
}