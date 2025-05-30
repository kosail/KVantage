package com.korealm.kvantage.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*

class AppThemeState(initialDarkTheme: Boolean) {
    var isDarkTheme by mutableStateOf(initialDarkTheme)
        private set

    fun toggleTheme() {
        isDarkTheme = !isDarkTheme
    }
}

@Composable
fun rememberAppThemeState(
    initialDarkTheme: Boolean = isSystemInDarkTheme()
): AppThemeState = remember { AppThemeState(initialDarkTheme) }