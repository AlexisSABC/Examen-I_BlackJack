package com.example.blackjack.ui.theme

import android.content.Context

class ThemePreferences(context: Context) {
    private val preferences = context.getSharedPreferences(
        "app_preferences",
        Context.MODE_PRIVATE)

    fun saveTheme(theme: AppTheme) {
        preferences.edit()
            .putString("selected_theme", theme.name)
            .apply()
    }

    fun getTheme(): AppTheme {
        val themeName = preferences.getString(
            "selected_theme",
            AppTheme.Classic.name)

        return runCatching {
            AppTheme.valueOf(themeName ?: AppTheme.Classic.name)
        }.getOrDefault(AppTheme.Classic)
    }
}