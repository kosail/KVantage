package com.korealm.kvantage.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

// Theme.kt
@Composable
fun AppTheme(
    themeType: ThemeType = ThemeType.GRUVBOX,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeType) {
        ThemeType.GRUVBOX -> if (darkTheme) gruvboxDarkColors() else gruvboxLightColors()
        ThemeType.MATERIAL -> if (darkTheme) materialYouDarkColors() else materialYouLightColors()
        ThemeType.DRACULA -> if (darkTheme) draculaDarkColors() else draculaLightColors()
        ThemeType.KANAGAWA -> if (darkTheme) kanagawaDarkColors() else kanagawaLightColors()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
