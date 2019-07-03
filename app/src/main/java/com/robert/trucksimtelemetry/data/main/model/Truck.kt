package com.robert.trucksimtelemetry.data.main.model

import com.google.gson.annotations.SerializedName

data class Truck(
    @SerializedName("id") val id: String? = null,
    @SerializedName("make") val make: String? = null,
    @SerializedName("model") val model: String? = null,
    @SerializedName("speed") val speed: Float? = null,
    @SerializedName("cruiseControlSpeed") val cruiseControlSpeed: Float? = null,
    @SerializedName("cruiseControlOn") val cruiseControlOn: Boolean? = false,
    @SerializedName("odometer") val odometer: Float? = null,
    @SerializedName("displayedGear") val displayedGear: Int? = null,
    @SerializedName("engineRpm") val engineRpm: Float? = null,
    @SerializedName("engineRpmMax") val engineRpmMax: Float? = null,
    @SerializedName("fuel") val fuel: Float? = null,
    @SerializedName("fuelCapacity") val fuelCapacity: Float? = null
) {
    fun getDisplayInfo(): String {
        return "ID: " + id.orEmpty() + "\n" +
                "make: " + make.orEmpty() + "\n" +
                "model: " + model.orEmpty() + "\n" +
                "speed: " + speed + "\n" +
                "cruiseControlSpeed: " + cruiseControlSpeed + "\n" +
                "cruiseControlOn: " + cruiseControlOn + "\n" +
                "odometer: " + odometer + "\n" +
                "displayedGear: " + displayedGear + "\n" +
                "engineRpm: " + engineRpm + "\n" +
                "engineRpmMax: " + engineRpmMax + "\n" +
                "fuel: " + fuel + "\n" +
                "fuelCapacity: " + fuelCapacity + "\n"
    }
}

