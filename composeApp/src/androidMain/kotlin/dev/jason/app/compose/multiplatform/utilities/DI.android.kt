package dev.jason.app.compose.multiplatform.utilities

import dev.jason.app.compose.multiplatform.MainActivity
import dev.jason.app.compose.multiplatform.viewmodel.HomeViewModel
import dev.jason.app.compose.multiplatform.viewmodel.NewNoteViewModel
import dev.jason.app.compose.multiplatform.viewmodel.NoteEditViewModel

actual val newNoteViewModel: NewNoteViewModel
    get() = MainActivity.newNoteViewModel

actual val homeViewModel: HomeViewModel
    get() = MainActivity.homeViewModel

actual val noteEditViewModel: NoteEditViewModel
    get() = MainActivity.noteEditViewModel