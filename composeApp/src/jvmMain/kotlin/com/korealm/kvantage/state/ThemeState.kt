package com.korealm.kvantage.state

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import com.korealm.kvantage.models.ThemeType

class AppThemeState(initialDarkTheme: Boolean) {
    var currentTheme by mutableStateOf(ThemeType.GRUVBOX)
    var isDarkTheme by mutableStateOf(initialDarkTheme)
        private set

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }

    fun setTheme(theme: ThemeType) {
        currentTheme = theme
    }
}

@Composable
fun rememberAppThemeState(
    initialDarkTheme: Boolean = isSystemInDarkTheme()
): AppThemeState = remember { AppThemeState(initialDarkTheme) }