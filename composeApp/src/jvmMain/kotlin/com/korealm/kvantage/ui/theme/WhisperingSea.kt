package com.korealm.kvantage.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// --- Whispering Sea Light Theme
val whisperSeaLightPrimary = Color(0xFF84a1b5)
val whisperSeaLightPrimaryContainer = Color(0xFFc5ced4)

val whisperSeaLightSecondary = Color(0xFFad8d71)
val whisperSeaLightSecondaryContainer = Color(0xFFd1c0b4)

val whisperSeaLightTertiary = Color(0xFF8686bd)
val whisperSeaLightTertiaryContainer = Color(0xFFb1b1c9)

val whisperSeaLightBackground = Color(0xFFFAFAFA)
val whisperSeaLightSurface = Color(0xFFF3F3F4)
val whisperSeaLightSurfaceVariant = Color(0xFFE0E0E2)

val whisperSeaLightText = Color(0xFF2C2C2E)
val whisperSeaLightError = Color(0xFFD27A7A)
val whisperSeaLightErrorContainer = Color(0xFFF9EAEA)

fun whisperingSeaLightColors() = lightColorScheme(
    primary = whisperSeaLightPrimary,
    onPrimary = whisperSeaLightText,
    primaryContainer = whisperSeaLightPrimaryContainer,
    onPrimaryContainer = whisperSeaLightText,

    secondary = whisperSeaLightSecondary,
    onSecondary = whisperSeaLightText,
    secondaryContainer = whisperSeaLightSecondaryContainer,
    onSecondaryContainer = whisperSeaLightText,

    tertiary = whisperSeaLightTertiary,
    onTertiary = whisperSeaLightText,
    tertiaryContainer = whisperSeaLightTertiaryContainer,
    onTertiaryContainer = whisperSeaLightText,

    background = whisperSeaLightBackground,
    onBackground = whisperSeaLightText,

    surface = whisperSeaLightSurface,
    onSurface = whisperSeaLightText,
    surfaceVariant = whisperSeaLightSurfaceVariant,
    onSurfaceVariant = Color(0xFF5F5F60),

    error = whisperSeaLightError,
    onError = whisperSeaLightText,
    errorContainer = whisperSeaLightErrorContainer,
    onErrorContainer = whisperSeaLightText
)


// --- Whispering Sea Dark Theme ---
val whisperSeaDarkPrimary = Color(0xFF8e9fab)
val whisperSeaDarkPrimaryContainer = Color(0xFF29323A)

val whisperSeaDarkSecondary = Color(0xFFC7BBB2)
val whisperSeaDarkSecondaryContainer = Color(0xFF3A302B)

val whisperSeaDarkTertiary = Color(0xFF9191b3)
val whisperSeaDarkTertiaryContainer = Color(0xFF2F2F3C)

val whisperSeaDarkBackground = Color(0xFF121416)
val whisperSeaDarkSurface = Color(0xFF1C1E21)
val whisperSeaDarkSurfaceVariant = Color(0xFF2C2E31)

val whisperSeaDarkText = Color(0xFFE6E6E8)
val whisperSeaDarkError = Color(0xFFE18C8C)
val whisperSeaDarkErrorContainer = Color(0xFF3B2B2B)

fun whisperingSeaDarkColors() = darkColorScheme(
    primary = whisperSeaDarkPrimary,
    onPrimary = whisperSeaDarkPrimaryContainer,
    primaryContainer = whisperSeaDarkPrimaryContainer,
    onPrimaryContainer = whisperSeaDarkText,

    secondary = whisperSeaDarkSecondary,
    onSecondary = whisperSeaDarkSecondaryContainer,
    secondaryContainer = whisperSeaDarkSecondaryContainer,
    onSecondaryContainer = whisperSeaDarkText,

    tertiary = whisperSeaDarkTertiary,
    onTertiary = whisperSeaDarkTertiaryContainer,
    tertiaryContainer = whisperSeaDarkTertiaryContainer,
    onTertiaryContainer = whisperSeaDarkText,

    background = whisperSeaDarkBackground,
    onBackground = whisperSeaDarkText,

    surface = whisperSeaDarkSurface,
    onSurface = whisperSeaDarkText,
    surfaceVariant = whisperSeaDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFAAAAAA),

    error = whisperSeaDarkError,
    onError = whisperSeaDarkText,
    errorContainer = whisperSeaDarkErrorContainer,
    onErrorContainer = whisperSeaDarkText
)