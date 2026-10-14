package com.example.blackjack.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    secondary = Color(0xFFCCC2DC),
    tertiary = Color(0xFFEFB8C8)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6650a4),
    secondary = Color(0xFF625b71),
    tertiary = Color(0xFF7D5260)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// Personalizados
private val Classic = lightColorScheme(
    primary = Color(0xFF6650A4), // Color de botones, elementos activos y cursor
    onPrimary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos

    secondary = Color(0xFFFFFFFF), // Color de botones secundarios y elementos de enfasis secundaios
    onSecondary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos secundarios

    tertiary = Color(0xFFFFFFFF), // Color de elementos destacados que complementan los colores principales

    background = Color(0xFFFFFFFF), // Color de fondo de app
    onBackground = Color(0xFF000000), // Color de textos e iconos sobre el fondo

    surface = Color(0xFFFFFFFF), // Color de tarjetas, menus, cuadros de dialogos y campos de texto
    onSurface = Color(0XFF000000), // Color del contenido sobre las tarjetas, cuadros de dialogos y campos de texto

    outline = Color(0xFF6650A4), // Borde de Outlinedtextfield
    outlineVariant = Color(0xFFFFFFFF) // Color de separadores y bordes sutiles
)

private val Dark = lightColorScheme(
    primary = Color(0xFF565656), // Color de botones, elementos activos y cursor
    onPrimary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos

    secondary = Color(0xFFFFFFFF), // Color de botones secundarios y elementos de enfasis secundaios
    onSecondary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos

    tertiary = Color(0xFFFFFFFF), // Color de elementos destacados que complementan los colores principales

    background = Color(0xFF181818), // Color de fondo de app
    onBackground = Color(0xFFFFFFFF), // Color de textos e iconos sobre el fondo

    surface = Color(0xFFFFFFFF), // Color de tarjetas, menus, cuadros de dialogos y campos de texto
    onSurface = Color(0xFFFFFFFF), // Color del contenido sobre las tarjetas, cuadros de dialogos y campos de texto

    outline = Color(0xFF565656), // Borde de Outlinedtextfield
    outlineVariant = Color(0xFFFFFFFF) // Color de separadores y bordes sutiles
)

private val Neon = lightColorScheme(
    primary = Color(0xFF00FFE1), // Color de botones, elementos activos y cursor
    onPrimary = Color(0xFF000000), // Color de textos e iconos de elementos activos

    secondary = Color(0xFFFFFFFF), // Color de botones secundarios y elementos de enfasis secundaios
    onSecondary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos

    tertiary = Color(0xFFFFFFFF), // Color de elementos destacados que complementan los colores principales

    background = Color(0xFF9800FF), // Color de fondo de app
    onBackground = Color(0xFFFFFFFF), // Color de textos e iconos sobre el fondo

    surface = Color(0xFFFFFFFF), // Color de tarjetas, menus, cuadros de dialogos y campos de texto
    onSurface = Color(0xFFFFFFFF), // Color del contenido sobre las tarjetas, cuadros de dialogos y campos de texto

    outline = Color(0xFF000000), // Borde de Outlinedtextfield
    outlineVariant = Color(0xFFFFFFFF) // Color de separadores y bordes sutiles
)

private val Casino = lightColorScheme(
    primary = Color(0xFFFFD700), // Color de botones, elementos activos y cursor
    onPrimary = Color(0xFF000000), // Color de textos e iconos de elementos activos

    secondary = Color(0xFFFFFFFF), // Color de botones secundarios y elementos de enfasis secundaios
    onSecondary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos

    tertiary = Color(0xFFFFFFFF), // Color de elementos destacados que complementan los colores principales

    background = Color(0xFFDC143C), // Color de fondo de app
    onBackground = Color(0xFFFFFFFF), // Color de textos e iconos sobre el fondo

    surface = Color(0xFFFFFFFF), // Color de tarjetas, menus, cuadros de dialogos y campos de texto
    onSurface = Color(0xFFFFFFFF), // Color del contenido sobre las tarjetas, cuadros de dialogos y campos de texto

    outline = Color(0xFFFFD700), // Borde de Outlinedtextfield
    outlineVariant = Color(0xFFFFFFFF) // Color de separadores y bordes sutiles
)

private val Quantum = lightColorScheme(
    primary = Color(0xFF0A13E8), // Color de botones, elementos activos y cursor
    onPrimary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos

    secondary = Color(0xFFFFDD55), // Color de botones secundarios y elementos de enfasis secundaios
    onSecondary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos

    tertiary = Color(0xFFFFFFFF), // Color de elementos destacados que complementan los colores principales

    background = Color(0xFF11C5E5), // Color de fondo de app
    onBackground = Color(0xFFFFFFFF), // Color de textos e iconos sobre el fondo

    surface = Color(0xFFFFFFFF), // Color de tarjetas, menus, cuadros de dialogos y campos de texto
    onSurface = Color(0xFFFFFFFF), // Color del contenido sobre las tarjetas, cuadros de dialogos y campos de texto

    outline = Color(0xFF0A13E8), // Borde de Outlinedtextfield
    outlineVariant = Color(0xFFFFFFFF) // Color de separadores y bordes sutiles
)

private val Bodega_Aurrera = lightColorScheme(
    primary = Color(0xFFEE1C55), // Color de botones, elementos activos y cursor
    onPrimary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos

    secondary = Color(0xFFFFFFFF), // Color de botones secundarios y elementos de enfasis secundaios
    onSecondary = Color(0xFFFFFFFF), // Color de textos e iconos de elementos activos

    tertiary = Color(0xFFFFFFFF), // Color de elementos destacados que complementan los colores principales

    background = Color(0xFF42B555), // Color de fondo de app
    onBackground = Color(0xFFFFFFFF), // Color de textos e iconos sobre el fondo

    surface = Color(0xFFFFFFFF), // Color de tarjetas, menus, cuadros de dialogos y campos de texto
    onSurface = Color(0xFFFFFFFF), // Color del contenido sobre las tarjetas, cuadros de dialogos y campos de texto

    outline = Color(0xFFEE1C55), // Borde de Outlinedtextfield
    outlineVariant = Color(0xFFFFFFFF) // Color de separadores y bordes sutiles
)

@Composable
fun ExamenIBlackJackTheme(
    selectedTheme: AppTheme,
    content: @Composable () -> Unit
) {
    val colorScheme = when (selectedTheme) {
        AppTheme.Classic -> Classic
        AppTheme.Dark -> Dark
        AppTheme.Neon -> Neon
        AppTheme.Casino -> Casino
        AppTheme.Quantum -> Quantum
        AppTheme.Bodega_Aurrera -> Bodega_Aurrera
        else -> {Classic}
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}