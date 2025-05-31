package dev.jason.app.compose.multiplatform.utilities

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
actual fun BackHandler(onBack: () -> Unit) {
    BackHandler {
        onBack()
    }
}

actual fun showDialog(showDialog: MutableState<Boolean>) {
    // DO ABSOLUTELY NOTHING
    // BECAUSE IT IS ONLY FOR DESKTOP
}