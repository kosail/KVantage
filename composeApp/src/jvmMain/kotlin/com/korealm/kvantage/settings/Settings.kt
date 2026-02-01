package com.korealm.kvantage.settings

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val isDarkMode: Boolean = false,
    val isAnimatedBackground: Boolean = true,
    val isRemainingBatteryLifeVisible: Boolean = true,
    val batteryName: String = "BAT0",
    val selectedThemeIndex: Int = 0
)