package com.korealm.kvantage.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// --- Whispering Sea Light Theme (macOS + GNOME inspired) ---
val whisperSeaLightPrimary = Color(0xFFBFC9D0)           // Soft Blue-Gray (main brand color)
val whisperSeaLightPrimaryContainer = Color(0xFFE6EBEE)  // Light Mist Blue

val whisperSeaLightSecondary = Color(0xFFB6ADA5)         // Warm Stone Beige
val whisperSeaLightSecondaryContainer = Color(0xFFECE8E5) // Soft Sand

val whisperSeaLightTertiary = Color(0xFFA7A7C5)          // Subtle Lavender Gray
val whisperSeaLightTertiaryContainer = Color(0xFFE9E9F2) // Clouded Lilac

val whisperSeaLightBackground = Color(0xFFFAFAFA)        // macOS-style Off White
val whisperSeaLightSurface = Color(0xFFF3F3F4)           // Very Light Gray
val whisperSeaLightSurfaceVariant = Color(0xFFE0E0E2)    // Neutral Divider Gray

val whisperSeaLightText = Color(0xFF2C2C2E)              // Deep Charcoal
val whisperSeaLightError = Color(0xFFD27A7A)             // Soft Red Clay
val whisperSeaLightErrorContainer = Color(0xFFF9EAEA)    // Blush Pink

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
    onSurfaceVariant = Color(0xFF5F5F60), // Neutral outline

    error = whisperSeaLightError,
    onError = whisperSeaLightText,
    errorContainer = whisperSeaLightErrorContainer,
    onErrorContainer = whisperSeaLightText
)


// --- Whispering Sea Dark Theme ---
val whisperSeaDarkPrimary = Color(0xFF8e9fab)            // Misty Blue-Gray
val whisperSeaDarkPrimaryContainer = Color(0xFF29323A)   // Deep Slate

val whisperSeaDarkSecondary = Color(0xFFC7BBB2)          // Warm Taupe
val whisperSeaDarkSecondaryContainer = Color(0xFF3A302B) // Dark Clay

val whisperSeaDarkTertiary = Color(0xFF9191b3)           // Steel Lavender
val whisperSeaDarkTertiaryContainer = Color(0xFF2F2F3C)  // Deep Indigo Gray

val whisperSeaDarkBackground = Color(0xFF121416)         // Almost Black
val whisperSeaDarkSurface = Color(0xFF1C1E21)            // Graphite Gray
val whisperSeaDarkSurfaceVariant = Color(0xFF2C2E31)     // Subdued Divider Gray

val whisperSeaDarkText = Color(0xFFE6E6E8)               // Mist White
val whisperSeaDarkError = Color(0xFFE18C8C)              // Warm Muted Red
val whisperSeaDarkErrorContainer = Color(0xFF3B2B2B)     // Deep Red Clay

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
    onSurfaceVariant = Color(0xFFAAAAAA),  // Subtle light divider

    error = whisperSeaDarkError,
    onError = whisperSeaDarkText,
    errorContainer = whisperSeaDarkErrorContainer,
    onErrorContainer = whisperSeaDarkText
)