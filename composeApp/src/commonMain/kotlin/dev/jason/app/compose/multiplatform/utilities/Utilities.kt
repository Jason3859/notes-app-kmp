package dev.jason.app.compose.multiplatform.utilities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

const val DATASTORE_FILE = "prefs.preferences_pb"

@Composable
expect fun BackHandler(onBack: () -> Unit)

expect fun showDialog(
    showDialog: MutableState<Boolean>
)