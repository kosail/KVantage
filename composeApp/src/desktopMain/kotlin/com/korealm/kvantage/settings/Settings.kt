package com.korealm.kvantage.settings

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val isDarkMode: Boolean = false,
    val isAnimatedBackground: Boolean = true,
    val selectedThemeIndex: Int = 0
)