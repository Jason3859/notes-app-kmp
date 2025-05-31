package dev.jason.app.compose.multiplatform

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.jason.app.compose.multiplatform.utilities.*
import dev.jason.app.compose.multiplatform.screen.*
import dev.jason.app.compose.multiplatform.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
@Preview
fun App() {

    val viewModel = homeViewModel

    val theme by viewModel.currentTheme.collectAsState(Theme.SYSTEM_DEFAULT)

    val isDarkMode = when (theme) {
        Theme.SYSTEM_DEFAULT -> isSystemInDarkTheme()
        Theme.DARK -> true
        Theme.LIGHT -> false
    }

    val navController = rememberNavController()

    val noteList by viewModel.getAll().collectAsState(emptyList())

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        AppTheme(
            darkTheme = isDarkMode
        ) {
            SharedTransitionLayout {
                NavHost(
                    navController = navController,
                    startDestination = HomeScreen,
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    composable<HomeScreen> {
                        HomeScreen(
                            notes = noteList,
                            navController = navController,
                            animatedVisibilityScope = this
                        )
                    }

                    composable<Settings> {
                        Settings(
                            navController = navController,
                            animatedVisibilityScope = this
                        )
                    }

                    val start = AnimatedContentTransitionScope.SlideDirection.Start
                    val end = AnimatedContentTransitionScope.SlideDirection.End

                    composable<AppInfo>(
                        enterTransition = {
                            slideIntoContainer(start)
                        },
                        exitTransition = {
                            slideOutOfContainer(end)
                        },
                        popEnterTransition = {
                            slideIntoContainer(start)
                        },
                        popExitTransition = {
                            slideOutOfContainer(end)
                        }
                    ) {
                        AppInfo(
                            navController = navController
                        )
                    }

                    composable<EditScreen> {
                        val args = it.toRoute<EditScreen>()

                        EditScreen(
                            id = args.id,
                            title = args.title,
                            note = args.content,
                            navController = navController,
                            animatedVisibilityScope = this
                        )
                    }

                    composable<NewNoteScreen> {
                        NewNoteScreen(
                            navController = navController,
                            animatedVisibilityScope = this
                        )
                    }
                }
            }
        }
    }
}