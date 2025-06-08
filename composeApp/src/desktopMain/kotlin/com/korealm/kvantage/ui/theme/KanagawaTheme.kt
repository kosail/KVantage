package com.korealm.kvantage.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


// Kanagawa Light Theme
val KanagawaLightPrimary = Color(0xFF7E9CD8)         // Wave Blue
val KanagawaLightPrimaryContainer = Color(0xFFA3B8EF)
val KanagawaLightSecondary = Color(0xFFD27E99)       // Sakura Pink
val KanagawaLightSecondaryContainer = Color(0xFFF5BCC8)
val KanagawaLightTertiary = Color(0xFF98BB6C)        // Spring Green
val KanagawaLightTertiaryContainer = Color(0xFFC3DCA9)

val KanagawaLightBackground = Color(0xFFFAF8EF)      // Ivory-style warm paper
val KanagawaLightSurface = Color(0xFFFFFFFF)         // Clean white
val KanagawaLightSurfaceVariant = Color(0xFFE5E5F0)  // Light lilac/gray

val KanagawaLightText = Color(0xFF1F1F28)            // Ink Black
val KanagawaLightError = Color(0xFFE46876)           // Autumn Red
val KanagawaLightErrorContainer = Color(0xFFFFDADA)

fun kanagawaLightColors() = lightColorScheme(
    primary = KanagawaLightPrimary,
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = KanagawaLightPrimaryContainer,
    onPrimaryContainer = KanagawaLightText,

    secondary = KanagawaLightSecondary,
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = KanagawaLightSecondaryContainer,
    onSecondaryContainer = KanagawaLightText,

    tertiary = KanagawaLightTertiary,
    onTertiary = Color(0xFF1E2D19),
    tertiaryContainer = KanagawaLightTertiaryContainer,
    onTertiaryContainer = KanagawaLightText,

    background = KanagawaLightBackground,
    onBackground = KanagawaLightText,

    surface = KanagawaLightSurface,
    onSurface = KanagawaLightText,
    surfaceVariant = KanagawaLightSurfaceVariant,
    onSurfaceVariant = Color(0xFF4A4A59),

    error = KanagawaLightError,
    onError = Color(0xFFFFFFFF),
    errorContainer = KanagawaLightErrorContainer,
    onErrorContainer = KanagawaLightText
)


// Kanagawa Dark Theme
val KanagawaDarkPrimary = Color(0xFFA3B8EF)          // Pale Wave Blue
val KanagawaDarkPrimaryContainer = Color(0xFF2A2A37) // Ink Layer
val KanagawaDarkSecondary = Color(0xFFF5BCC8)        // Sakura Bloom
val KanagawaDarkSecondaryContainer = Color(0xFF4A3F47)
val KanagawaDarkTertiary = Color(0xFFC3DCA9)         // Leaf Green
val KanagawaDarkTertiaryContainer = Color(0xFF37473A)

val KanagawaDarkBackground = Color(0xFF1F1F28)       // Sumi Ink
val KanagawaDarkSurface = Color(0xFF2A2A37)          // Sumi Ink L1
val KanagawaDarkSurfaceVariant = Color(0xFF363646)   // Gray layer

val KanagawaDarkText = Color(0xFFDCD7BA)             // Fuji White
val KanagawaDarkError = Color(0xFFFF7B93)            // Cherry Blossom Red
val KanagawaDarkErrorContainer = Color(0xFF4A2A2E)

fun kanagawaDarkColors() = darkColorScheme(
    primary = KanagawaDarkPrimary,
    onPrimary = Color(0xFF1C1F2A),
    primaryContainer = KanagawaDarkPrimaryContainer,
    onPrimaryContainer = KanagawaDarkText,

    secondary = KanagawaDarkSecondary,
    onSecondary = Color(0xFF382E33),
    secondaryContainer = KanagawaDarkSecondaryContainer,
    onSecondaryContainer = KanagawaDarkText,

    tertiary = KanagawaDarkTertiary,
    onTertiary = Color(0xFF28322B),
    tertiaryContainer = KanagawaDarkTertiaryContainer,
    onTertiaryContainer = KanagawaDarkText,

    background = KanagawaDarkBackground,
    onBackground = KanagawaDarkText,

    surface = KanagawaDarkSurface,
    onSurface = KanagawaDarkText,
    surfaceVariant = KanagawaDarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFC8C0B8), // Stone Gray contrast

    error = KanagawaDarkError,
    onError = Color(0xFF1A0A0C),
    errorContainer = KanagawaDarkErrorContainer,
    onErrorContainer = KanagawaDarkText
)
