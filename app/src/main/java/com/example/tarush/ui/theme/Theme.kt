package com.example.tarush.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(56, 83, 204, 100), // Blue
    onPrimary = Color.White,
    primaryContainer = Color(245, 245, 245, 100),
    onPrimaryContainer = Color.Black,

    secondary = Color(104, 0, 184, 100), // Purple
    onSecondary = Color.White,
    secondaryContainer = Color(200, 111, 254, 100), // Light Purple
    onSecondaryContainer = Color.White,

    tertiary = Color(242, 120, 16, 100), // Orange
    tertiaryContainer = Color(254, 248, 246, 100),


    background = Color.White,
    onBackground = Color.Gray,

)

@Composable
fun TarushTheme(
    content: @Composable () -> Unit
) {
    // Always use LightColorScheme regardless of system settings
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}