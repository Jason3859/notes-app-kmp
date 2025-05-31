package dev.jason.app.compose.multiplatform.utilities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent

@Composable
actual fun BackHandler(onBack: () -> Unit) {
    var lastKey: KeyEvent? by remember { mutableStateOf(null) }

    LaunchedEffect(lastKey) {
        Modifier.onPreviewKeyEvent {
            lastKey = it
            false
        }
    }

    if (lastKey?.key == Key.Escape) {
        onBack()
    }
}

actual fun showDialog(showDialog: MutableState<Boolean>) {
    showDialog.value = true
}