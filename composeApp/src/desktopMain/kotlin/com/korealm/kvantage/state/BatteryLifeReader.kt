package com.korealm.kvantage.state

import androidx.compose.runtime.*
import java.io.File

class BatteryLifeReader(initialBatteryName: String) {
    private val BATTERY_PATH = "/sys/class/power_supply/"

    var batteryName by mutableStateOf(initialBatteryName)
        private set

    var batterylife by mutableStateOf(0f)
        private set

    fun updateBatteryName(newName: String) {
        batteryName = newName
        refreshBatteryLife()
    }

    fun refreshBatteryLife() {
        batterylife = calculateRemainingLife()
    }

    private fun calculateRemainingLife(): Float {
        val fullPath = BATTERY_PATH + batteryName
        val energyFullDesignFile = File("$fullPath/energy_full_design")
        val energyFullFile = File("$fullPath/energy_full")

        if (!energyFullDesignFile.exists() || !energyFullFile.exists()) return -1f

        return try {
            val energyFullDesign = energyFullDesignFile.readText().trim().toLongOrNull() ?: 0
            val energyFull = energyFullFile.readText().trim().toLongOrNull() ?: 0

            if (energyFullDesign == 0L) 0f else ((energyFull * 100f) / energyFullDesign)
        } catch (e: Exception) {
            println("Battery info error for $batteryName: ${e.message}")
            0f
        }
    }
}

@Composable
fun rememberBatteryLifeState(batteryName: String): BatteryLifeReader {
    val reader = remember { BatteryLifeReader(batteryName) }

    // Auto-refresh when batteryName changes from outside
    LaunchedEffect(batteryName) {
        if (reader.batteryName != batteryName) {
            reader.updateBatteryName(batteryName)
        }
    }

    // Initial load
    LaunchedEffect(Unit) {
        reader.refreshBatteryLife()
    }

    return reader
}