package dev.jason.app.compose.multiplatform.utilities

import dev.jason.app.compose.multiplatform.viewmodel.HomeViewModel
import dev.jason.app.compose.multiplatform.viewmodel.NewNoteViewModel
import dev.jason.app.compose.multiplatform.viewmodel.NoteEditViewModel

expect val newNoteViewModel: NewNoteViewModel
expect val noteEditViewModel: NoteEditViewModel
expect val homeViewModel: HomeViewModel