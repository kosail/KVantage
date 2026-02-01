package com.korealm.kvantage.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


// Material You - Light Theme
val MaterialYouLightPrimary = Color(0xFF3366DD)          // Soft blue
val MaterialYouLightPrimaryContainer = Color(0xFFDCE4FF) // Pale blue
val MaterialYouLightSecondary = Color(0xFFB07DF2)        // Soft purple (lavender)
val MaterialYouLightSecondaryContainer = Color(0xFFEBDDFF)
val MaterialYouLightTertiary = Color(0xFFFFB4A3)          // Soft coral-orange
val MaterialYouLightTertiaryContainer = Color(0xFFFFDAD2)

val MaterialYouLightBackground = Color(0xFFFAFAFA)       // Paper white
val MaterialYouLightSurface = Color(0xFFFFFFFF)          // True white surface
val MaterialYouLightSurfaceVariant = Color(0xFFE0E0E0)   // Subtle light gray

val MaterialYouLightText = Color(0xFF1A1C1E)              // Neutral gray-black
val MaterialYouLightError = Color(0xFFB3261E)             // Standard error red
val MaterialYouLightErrorContainer = Color(0xFFFFDAD4)

fun materialYouLightColors() = lightColorScheme(
    primary = MaterialYouLightPrimary,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = MaterialYouLightPrimaryContainer,
    onPrimaryContainer = MaterialYouLightText,

    secondary = MaterialYouLightSecondary,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = MaterialYouLightSecondaryContainer,
    onSecondaryContainer = MaterialYouLightText,

    tertiary = MaterialYouLightTertiary,
    onTertiary = Color(0xFF381E1F),
    tertiaryContainer = MaterialYouLightTertiaryContainer,
    onTertiaryContainer = MaterialYouLightText,

    background = MaterialYouLightBackground,
    onBackground = MaterialYouLightText,

    surface = MaterialYouLightSurface,
    onSurface = MaterialYouLightText,
    surfaceVariant = MaterialYouLightSurfaceVariant,
    onSurfaceVariant = Color(0xFF4A4A4A),

    error = MaterialYouLightError,
    onError = Color(0xFFFFFFFF),
    errorContainer = MaterialYouLightErrorContainer,
    onErrorContainer = MaterialYouLightText
)


// Material You - Dark Theme
val MaterialYouDarkPrimary = Color(0xFF9CB9FF)            // Light sky blue
val MaterialYouDarkPrimaryContainer = Color(0xFF2A3E71)   // Deep blue
val MaterialYouDarkSecondary = Color(0xFFD0BCFF)          // Bright lavender
val MaterialYouDarkSecondaryContainer = Color(0xFF4A4458)
val MaterialYouDarkTertiary = Color(0xFFFFB59E)           // Coral
val MaterialYouDarkTertiaryContainer = Color(0xFF5A2D27)

val MaterialYouDarkBackground = Color(0xFF121212)         // Material Black
val MaterialYouDarkSurface = Color(0xFF1E1E1E)            // Dark surface
val MaterialYouDarkSurfaceVariant = Color(0xFF323232)     // Slightly lighter card

val MaterialYouDarkText = Color(0xFFE6E1E5)               // Warm light gray
val MaterialYouDarkError = Color(0xFFFFB4AB)              // Soft light red
val MaterialYouDarkErrorContainer = Color(0xFF8C1D18)

fun materialYouDarkColors() = darkColorScheme(
    primary = MaterialYouDarkPrimary,
    onPrimary = Color(0xFF0B254F),
    primaryContainer = MaterialYouDarkPrimaryContainer,
    onPrimaryContainer = MaterialYouDarkText,

    secondary = MaterialYouDarkSecondary,
    onSecondary = Color(0xFF332D41),
    secondaryContainer = MaterialYouDarkSecondaryContainer,
    onSecondaryContainer = MaterialYouDarkText,

    tertiary = MaterialYouDarkTertiary,
    onTertiary = Color(0xFF442926),
    tertiaryContainer = MaterialYouDarkTertiaryContainer,
    onTertiaryContainer = MaterialYouDarkText,

    background = MaterialYouDarkBackground,
    onBackground = MaterialYouDarkText,

    surface = MaterialYouDarkSurface,
    onSurface = MaterialYouDarkText,
    surfaceVariant = MaterialYouDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFCFCFCF),

    error = MaterialYouDarkError,
    onError = Color(0xFF1F0A0A),
    errorContainer = MaterialYouDarkErrorContainer,
    onErrorContainer = MaterialYouDarkText
)
