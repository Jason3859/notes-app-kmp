package dev.jason.app.compose.multiplatform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.jason.app.compose.multiplatform.utilities.AndroidTheme
import dev.jason.app.compose.multiplatform.viewmodel.HomeViewModel
import dev.jason.app.compose.multiplatform.viewmodel.NewNoteViewModel
import dev.jason.app.compose.multiplatform.viewmodel.NoteEditViewModel

class MainActivity : ComponentActivity() {

    companion object {

        private fun CreationExtras.application() = (this[APPLICATION_KEY] as NotesApplication)
        internal lateinit var newNoteViewModel: NewNoteViewModel
        internal lateinit var noteEditViewModel: NoteEditViewModel

        internal lateinit var homeViewModel: HomeViewModel

        val viewModelFactory = viewModelFactory {
            initializer {
                NewNoteViewModel(
                    notesDao = application().database.notesDao()
                )
            }

            initializer {
                NoteEditViewModel(
                    application().database.notesDao()
                )
            }

            initializer {
                HomeViewModel(
                    application().database.notesDao(),
                    AndroidTheme(application().dataStore),
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            newNoteViewModel = viewModel<NewNoteViewModel>(factory = viewModelFactory)
            noteEditViewModel = viewModel<NoteEditViewModel>(factory = viewModelFactory)
            homeViewModel = viewModel<HomeViewModel>(factory = viewModelFactory)

            App()
        }
    }
}