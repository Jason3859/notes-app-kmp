@file:OptIn(ExperimentalMaterial3Api::class)

package dev.jason.app.compose.multiplatform.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.jason.app.compose.multiplatform.utilities.*
import dev.jason.app.compose.multiplatform.utilities.SharedBounds.NEW_NOTE_SCREEN
import dev.jason.app.compose.multiplatform.database.Notes
import notes.composeapp.generated.resources.Res
import notes.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.HomeScreen(
    notes: List<Notes>,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.app_name)
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Settings)
                        },
                        modifier = modifier.sharedBounds(
                            rememberSharedContentState(SharedBounds.SETTINGS),
                            animatedVisibilityScope
                        )
                    ) {
                        Icon(Icons.Default.Settings, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surfaceContainerLow)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = modifier.sharedBounds(
                    sharedContentState = rememberSharedContentState(NEW_NOTE_SCREEN),
                    animatedVisibilityScope = animatedVisibilityScope
                ),
                onClick = {
                    navController.navigate(NewNoteScreen)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->

        if (notes.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                contentPadding = paddingValues,
                modifier = modifier.padding(8.dp)
            ) {
                items(
                    items = notes,
                    key = { it.id }
                ) {
                    Card(
                        modifier = modifier
                            .padding(4.dp)
                            .sharedBounds(
                                rememberSharedContentState(it.id),
                                animatedVisibilityScope
                            ),
                        onClick = {
                            navController.navigate(
                                EditScreen(
                                    id = it.id,
                                    title = it.title,
                                    content = it.content
                                )
                            )
                        }
                    ) {
                        Text(
                            text = it.title,
                            fontSize = 15.sp,
                            modifier = modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        )
                    }
                }
            }
        } else {
            Box(modifier.fillMaxSize(), Alignment.Center) {
                Text("Click + to add note", fontSize = 20.sp)
            }
        }
    }
}