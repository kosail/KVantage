package com.korealm.kvantage.state

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.korealm.kvantage.models.ThemeType
import com.korealm.kvantage.utils.AppLogger

class AppThemeState(initialDarkTheme: Boolean) {
    var currentTheme by mutableStateOf(ThemeType.SEA)
    var isDarkTheme by mutableStateOf(initialDarkTheme)
        private set

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
        AppLogger.debug("ThemeState", "Toggled theme to ${if (isDarkTheme) "dark" else "light"}")
    }

    fun setTheme(theme: ThemeType) {
        currentTheme = theme
        AppLogger.debug("ThemeState", "Set theme to $theme")
    }
}

@Composable
fun rememberAppThemeState(
    initialDarkTheme: Boolean = isSystemInDarkTheme()
): AppThemeState = remember { AppThemeState(initialDarkTheme) }