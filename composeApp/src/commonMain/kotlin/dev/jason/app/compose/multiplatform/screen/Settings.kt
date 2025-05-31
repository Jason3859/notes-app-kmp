package dev.jason.app.compose.multiplatform.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.jason.app.compose.multiplatform.utilities.*
import notes.composeapp.generated.resources.Res
import notes.composeapp.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.Settings(
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    val viewModel = homeViewModel

    val showDesktopDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .sharedBounds(
                sharedContentState = rememberSharedContentState(SharedBounds.SETTINGS),
                animatedVisibilityScope = animatedVisibilityScope
            ),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.surfaceContainerLow),
                title = {
                    Text("Settings")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(AppInfo)
                    }) {
                        Icon(Icons.Default.Info, null)
                    }
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = modifier.fillMaxSize().padding(paddingValues)
        ) {
            item {
                var expanded by remember { mutableStateOf(false) }

                val currentTheme by viewModel.currentTheme.collectAsState(Theme.SYSTEM_DEFAULT)

                Card(
                    modifier = modifier
                        .fillParentMaxWidth()
                        .padding(20.dp),
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Column(
                        modifier = modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text("App Theme")
                            Spacer(modifier.weight(1f))
                            Icon(
                                if (expanded)
                                    Icons.Default.ArrowUpward
                                else
                                    Icons.Default.ArrowDownward,
                                null
                            )
                        }

                        if (expanded) {
                            Spacer(modifier.height(16.dp))
                        }

                        AnimatedVisibility(
                            visible = expanded
                        ) {

                            Column(
                                modifier = modifier.fillParentMaxWidth(),
                            ) {

                                val themes = listOf(
                                    Theme.SYSTEM_DEFAULT,
                                    Theme.LIGHT,
                                    Theme.DARK,
                                )

                                listOf(
                                    "System Default",
                                    "Light",
                                    "Dark"
                                ).forEachIndexed { index, theme ->
                                    Button(
                                        onClick = {
                                            viewModel.setTheme(themes[index])
                                            showDialog(showDesktopDialog)
                                        },
                                        modifier = modifier.fillParentMaxWidth().padding(horizontal = 35.dp)
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            if (currentTheme == themes[index]) {
                                                Icon(Icons.Default.Done, null)
                                            }
                                            Text(theme)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showDesktopDialog.value,
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
                onDismissRequest = { showDesktopDialog.value = false },
                title = { Text("Apply change") },
                text = { Text("Restart app to apply change") },
                confirmButton = {
                    TextButton(onClick = {
                        showDesktopDialog.value = false
                    }) {
                        Text("Ok")
                    }
                }
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfo(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("About")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.app_name),
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("v1.0")
                Text(
                    text = "Owned and developed by Jason"
                )
                Text("Since 2025")
            }
        }
    }
}