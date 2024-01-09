package com.twobit.driver.settings

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    var unitType: UnitType = UnitType.METRIC,
    var sensors: Map<SensorType, Boolean> = SensorType.values().associate { it to true }
    )

enum class UnitType {
    IMPERIAL, METRIC
}


