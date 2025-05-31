package dev.jason.app.compose.multiplatform.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import dev.jason.app.compose.multiplatform.utilities.noteEditViewModel
import notes.composeapp.generated.resources.Res
import notes.composeapp.generated.resources.edit_screen
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.EditScreen(
    id: Int,
    title: String,
    note: String,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    val viewModel = noteEditViewModel

    var titleState by rememberSaveable { mutableStateOf(title) }
    var contentState by rememberSaveable { mutableStateOf(note) }

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var deleteClicked by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .sharedBounds(
                sharedContentState = rememberSharedContentState(id),
                animatedVisibilityScope = animatedVisibilityScope
            ),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surfaceContainerLow),
                title = {
                    Text(stringResource(Res.string.edit_screen))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            when {
                                titleState != title -> {
                                    showDialog = true
                                }

                                contentState != note -> {
                                    showDialog = true
                                }

                                else -> navController.navigateUp()
                            }
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        deleteClicked = true
                    }) {
                        Icon(Icons.Default.Delete, null)
                    }

                    IconButton(onClick = {
                        viewModel.update(id, titleState, contentState)
                        navController.navigateUp()
                    }) {
                        Icon(Icons.Default.Done, null)
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = paddingValues
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
                    value = titleState,
                    onValueChange = { newTitle ->
                        titleState = newTitle
                    },
                    colors = transparentColors,
                    modifier = modifier.fillParentMaxWidth(),
                    singleLine = true,
                    label = {
                        Text("Title")
                    }
                )

                TextField(
                    value = contentState,
                    onValueChange = { newContent ->
                        contentState = newContent
                    },
                    colors = transparentColors,
                    modifier = modifier.fillParentMaxWidth().fillParentMaxHeight(),
                    label = {
                        Text("Note")
                    }
                )

                BackHandler {
                    when {
                        titleState != title -> {
                            showDialog = true
                        }

                        contentState != note -> {
                            showDialog = true
                        }

                        else -> navController.navigateUp()
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = deleteClicked,
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
                onDismissRequest = { deleteClicked = false },
                title = { Text("Delete note?") },
                text = { Text("This action cannot be undone") },
                confirmButton = {
                    TextButton(onClick = {
                        viewModel.delete(id)
                        navController.navigateUp()
                    }) {
                        Text("Ok")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        deleteClicked = false
                    }) {
                        Text("Cancel")
                    }
                }
            )
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
                text = { Text("This action cannot be undone") },
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