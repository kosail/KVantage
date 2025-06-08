package com.korealm.kvantage.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val GruvboxLightPrimary = Color(0xFF98971A)       // Yellow-Green
val GruvboxLightPrimaryContainer = Color(0xFFD5C4A1) // Light tan (light neutral)
val GruvboxLightSecondary = Color(0xFFB16286)     // Purple
val GruvboxLightSecondaryContainer = Color(0xFFEBD6F4) // Light Lavender
val GruvboxLightTertiary = Color(0xFF458588)      // Aqua Blue
val GruvboxLightTertiaryContainer = Color(0xFFCCE5E5) // Soft Aqua background

val GruvboxLightBackground = Color(0xFFFBF1C7)     // Gruvbox Light Background
val GruvboxLightSurface = Color(0xFFFEFDFC)        // Near-white paper
val GruvboxLightSurfaceVariant = Color(0xFFD5C4A1) // Soft tan/beige

val GruvboxLightText = Color(0xFF3C3836)           // Gruvbox Dark Gray
val GruvboxLightError = Color(0xFFCC241D)          // Gruvbox Red
val GruvboxLightErrorContainer = Color(0xFFFFDAD6) // Light red container

fun gruvboxLightColors() = lightColorScheme(
    primary = GruvboxLightPrimary,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = GruvboxLightPrimaryContainer,
    onPrimaryContainer = GruvboxLightText,

    secondary = GruvboxLightSecondary,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = GruvboxLightSecondaryContainer,
    onSecondaryContainer = GruvboxLightText,

    tertiary = GruvboxLightTertiary,
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = GruvboxLightTertiaryContainer,
    onTertiaryContainer = GruvboxLightText,

    background = GruvboxLightBackground,
    onBackground = GruvboxLightText,

    surface = GruvboxLightSurface,
    onSurface = GruvboxLightText,
    surfaceVariant = GruvboxLightSurfaceVariant,
    onSurfaceVariant = Color(0xFF504945), // Gruvbox gray-brown

    error = GruvboxLightError,
    onError = Color(0xFFFFFFFF),
    errorContainer = GruvboxLightErrorContainer,
    onErrorContainer = GruvboxLightText
)


// Gruvbox Dark Theme
val GruvboxDarkPrimary = Color(0xFFB8BB26)        // Yellow-Green (brighter for dark bg)
val GruvboxDarkPrimaryContainer = Color(0xFF3C3836) // Dark brown
val GruvboxDarkSecondary = Color(0xFFD3869B)      // Pinkish Purple
val GruvboxDarkSecondaryContainer = Color(0xFF4B3F45) // Deep Purple Background
val GruvboxDarkTertiary = Color(0xFF83A598)       // Teal Blue
val GruvboxDarkTertiaryContainer = Color(0xFF2D3A3A) // Dark Aqua Background

val GruvboxDarkBackground = Color(0xFF282828)     // Gruvbox Dark Background
val GruvboxDarkSurface = Color(0xFF32302F)        // Slightly lighter dark gray
val GruvboxDarkSurfaceVariant = Color(0xFF504945) // Dark brown-gray

val GruvboxDarkText = Color(0xFFEBDBB2)           // Soft warm white
val GruvboxDarkError = Color(0xFFFB4934)          // Gruvbox Bright Red
val GruvboxDarkErrorContainer = Color(0xFF3C0000) // Dark red container

fun gruvboxDarkColors() = darkColorScheme(
    primary = GruvboxDarkPrimary,
    onPrimary = Color(0xFF1D2021),
    primaryContainer = GruvboxDarkPrimaryContainer,
    onPrimaryContainer = GruvboxDarkText,

    secondary = GruvboxDarkSecondary,
    onSecondary = Color(0xFF1D2021),
    secondaryContainer = GruvboxDarkSecondaryContainer,
    onSecondaryContainer = GruvboxDarkText,

    tertiary = GruvboxDarkTertiary,
    onTertiary = Color(0xFF1D2021),
    tertiaryContainer = GruvboxDarkTertiaryContainer,
    onTertiaryContainer = GruvboxDarkText,

    background = GruvboxDarkBackground,
    onBackground = GruvboxDarkText,

    surface = GruvboxDarkSurface,
    onSurface = GruvboxDarkText,
    surfaceVariant = GruvboxDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFD5C4A1), // Reversed for better contrast

    error = GruvboxDarkError,
    onError = Color(0xFF1D2021),
    errorContainer = GruvboxDarkErrorContainer,
    onErrorContainer = GruvboxDarkText
)