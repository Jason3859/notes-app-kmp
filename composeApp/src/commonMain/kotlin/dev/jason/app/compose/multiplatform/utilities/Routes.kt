package dev.jason.app.compose.multiplatform.utilities

import kotlinx.serialization.Serializable

@Serializable
data object HomeScreen

@Serializable
data object NewNoteScreen

@Serializable
data object Settings

@Serializable
data object AppInfo

@Serializable
data class EditScreen(
    val id: Int,
    val title: String,
    val content: String
)