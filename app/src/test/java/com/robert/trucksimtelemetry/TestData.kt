package com.robert.trucksimtelemetry

import com.robert.trucksimtelemetry.data.main.model.Truck
import okhttp3.MediaType
import okhttp3.ResponseBody

val errorResponseBody = ResponseBody.create(MediaType.parse(""), "Error")

val testTruck = Truck(
    id = "ID",
    make = "make",
    model = "model",
    speed = 100.0f,
    cruiseControlOn = true,
    cruiseControlSpeed = 60f,
    displayedGear = 10,
    engineRpm = 7000f,
    engineRpmMax = 8000f
)