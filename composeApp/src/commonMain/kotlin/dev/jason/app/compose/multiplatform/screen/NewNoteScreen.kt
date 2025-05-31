package dev.jason.app.compose.multiplatform.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import dev.jason.app.compose.multiplatform.utilities.BackHandler
import dev.jason.app.compose.multiplatform.utilities.SharedBounds.NEW_NOTE_SCREEN
import dev.jason.app.compose.multiplatform.utilities.newNoteViewModel
import notes.composeapp.generated.resources.Res
import notes.composeapp.generated.resources.new_note_screen
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.NewNoteScreen(
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier
) {
    val viewModel = newNoteViewModel

    var title by rememberSaveable { mutableStateOf("") }
    var note by rememberSaveable { mutableStateOf("") }

    var showDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .sharedBounds(
                sharedContentState = rememberSharedContentState(NEW_NOTE_SCREEN),
                animatedVisibilityScope = animatedVisibilityScope
            ),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surfaceContainerLow),
                title = {
                    Text(
                        text = stringResource(Res.string.new_note_screen)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            when {
                                title.isNotEmpty() -> {
                                    showDialog = true
                                }
                                note.isNotEmpty() -> {
                                    showDialog = true
                                }

                                else -> navController.navigateUp()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.save(title, note)
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Default.Done, null)
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = modifier.fillMaxSize()
        ) {


            item {
                val transparentColors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )

                TextField(
                    value = title,
                    onValueChange = { newTitle ->
                        title = newTitle
                    },
                    modifier = modifier.fillParentMaxWidth(),
                    colors = transparentColors,
                    label = {
                        Text("Title")
                    }
                )

                TextField(
                    value = note,
                    onValueChange = { newContent ->
                        note = newContent
                    },
                    modifier = modifier.fillParentMaxSize(),
                    colors = transparentColors,
                    label = {
                        Text("Note")
                    }
                )

                BackHandler {
                    when {
                        title.isNotEmpty() -> {
                            showDialog = true
                        }

                        note.isNotEmpty() -> {
                            showDialog = true
                        }

                        else -> navController.navigateUp()
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showDialog,
            enter = scaleIn(
                initialScale = 0.5f,
                animationSpec = tween(1500)
            ),
            exit = scaleOut(
                targetScale = 0.5f,
                animationSpec = tween(1500)
            )
        ) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Discard note?") },
                text = { Text("This cannot be undone") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Text("Ok")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}