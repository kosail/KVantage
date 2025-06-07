package com.korealm.kvantage.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DraculaLightPrimary = Color(0xFF6272A4)           // Muted Indigo (from Comment)
val DraculaLightPrimaryContainer = Color(0xFFDEE2F7)  // Very Light Indigo
val DraculaLightSecondary = Color(0xFFFFB86C)         // Orange
val DraculaLightSecondaryContainer = Color(0xFFFFE4CC) // Soft Peach
val DraculaLightTertiary = Color(0xFF8BE9FD)          // Cyan
val DraculaLightTertiaryContainer = Color(0xFFD6F8FF) // Pale Cyan

val DraculaLightBackground = Color(0xFFF8F8F2)         // White (from Dracula text)
val DraculaLightSurface = Color(0xFFFFFFFF)            // Bright white surface
val DraculaLightSurfaceVariant = Color(0xFFE2E2F2)     // Light gray-blue

val DraculaLightText = Color(0xFF282A36)               // Dark background text
val DraculaLightError = Color(0xFFFF5555)              // Red
val DraculaLightErrorContainer = Color(0xFFFFDADA)     // Light Red container

fun draculaLightColors() = lightColorScheme(
    primary = DraculaLightPrimary,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = DraculaLightPrimaryContainer,
    onPrimaryContainer = DraculaLightText,

    secondary = DraculaLightSecondary,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = DraculaLightSecondaryContainer,
    onSecondaryContainer = DraculaLightText,

    tertiary = DraculaLightTertiary,
    onTertiary = Color(0xFF000000),
    tertiaryContainer = DraculaLightTertiaryContainer,
    onTertiaryContainer = DraculaLightText,

    background = DraculaLightBackground,
    onBackground = DraculaLightText,

    surface = DraculaLightSurface,
    onSurface = DraculaLightText,
    surfaceVariant = DraculaLightSurfaceVariant,
    onSurfaceVariant = Color(0xFF3E4459), // Mid-dark bluish gray

    error = DraculaLightError,
    onError = Color(0xFFFFFFFF),
    errorContainer = DraculaLightErrorContainer,
    onErrorContainer = DraculaLightText
)


// Dracula Dark Theme
val DraculaDarkPrimary = Color(0xFFBD93F9)             // Purple
val DraculaDarkPrimaryContainer = Color(0xFF44475A)    // Current line
val DraculaDarkSecondary = Color(0xFFFF79C6)           // Pink
val DraculaDarkSecondaryContainer = Color(0xFF5A4A5A)  // Desaturated pinkish-purple
val DraculaDarkTertiary = Color(0xFF8BE9FD)            // Cyan
val DraculaDarkTertiaryContainer = Color(0xFF2A3B3F)   // Deep teal

val DraculaDarkBackground = Color(0xFF282A36)          // Background
val DraculaDarkSurface = Color(0xFF323443)             // Slightly lighter for surface
val DraculaDarkSurfaceVariant = Color(0xFF44475A)      // Current line

val DraculaDarkText = Color(0xFFF8F8F2)                // Foreground
val DraculaDarkError = Color(0xFFFF5555)               // Red
val DraculaDarkErrorContainer = Color(0xFF3A1E1E)      // Very dark red

fun draculaDarkColors() = darkColorScheme(
    primary = DraculaDarkPrimary,
    onPrimary = Color(0xFF1C1E26),
    primaryContainer = DraculaDarkPrimaryContainer,
    onPrimaryContainer = DraculaDarkText,

    secondary = DraculaDarkSecondary,
    onSecondary = Color(0xFF1E1E2A),
    secondaryContainer = DraculaDarkSecondaryContainer,
    onSecondaryContainer = DraculaDarkText,

    tertiary = DraculaDarkTertiary,
    onTertiary = Color(0xFF1B2B30),
    tertiaryContainer = DraculaDarkTertiaryContainer,
    onTertiaryContainer = DraculaDarkText,

    background = DraculaDarkBackground,
    onBackground = DraculaDarkText,

    surface = DraculaDarkSurface,
    onSurface = DraculaDarkText,
    surfaceVariant = DraculaDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFC0C0C0), // Soft gray-blue for contrast

    error = DraculaDarkError,
    onError = Color(0xFF1E1E1E),
    errorContainer = DraculaDarkErrorContainer,
    onErrorContainer = DraculaDarkText
)
