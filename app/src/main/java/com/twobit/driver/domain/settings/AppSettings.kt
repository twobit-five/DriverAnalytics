package com.twobit.driver.domain.settings

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    var unitType: UnitType = UnitType.METRIC,
    var driversAge: Int = -1,
    var sensors: Map<SensorType, Boolean> = SensorType.values().associate { it to true }
    )

enum class UnitType {
    IMPERIAL, METRIC
}

enum class  SensorType {
    ACCELEROMETER, GYROSCOPE, MAGNETOMETER, GPS
}
