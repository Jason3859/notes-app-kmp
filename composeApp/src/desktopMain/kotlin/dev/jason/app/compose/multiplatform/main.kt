package dev.jason.app.compose.multiplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() {
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Notes",
        ) {
            App()
        }
    }
}