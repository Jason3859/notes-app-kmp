package dev.jason.app.compose.multiplatform.utilities

interface LocalTheme {
    suspend fun getTheme(): Theme
    suspend fun setTheme(theme: Theme)
}