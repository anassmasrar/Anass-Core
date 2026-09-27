package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AnassCoreDarkColorScheme = darkColorScheme(
    primary = XBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF0D2538),
    onPrimaryContainer = Color(0xFF8AD1FF),
    secondary = AnassBotCyan,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF16181C),
    onSecondaryContainer = XTextPrimary,
    tertiary = UltraGoldAccent,
    onTertiary = Color.Black,
    background = XBlack,
    onBackground = XTextPrimary,
    surface = XBlack,
    onSurface = XTextPrimary,
    surfaceVariant = XCardSurface,
    onSurfaceVariant = XTextSecondary,
    outline = XDivider,
    outlineVariant = XSubtleBorder
)

private val AnassCoreLightColorScheme = lightColorScheme(
    primary = XBlue,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE8F5FD),
    onPrimaryContainer = Color(0xFF0F4C81),
    secondary = Color(0xFF007A87),
    onSecondary = Color.White,
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF0F1419),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F1419),
    surfaceVariant = Color(0xFFF7F9F9),
    onSurfaceVariant = Color(0xFF536471),
    outline = Color(0xFFCFD9DE),
    outlineVariant = Color(0xFFEFF3F4)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true, // X.com is iconic in dark mode
    dynamicColor: Boolean = false, // Keep authentic X aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) AnassCoreDarkColorScheme else AnassCoreLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
