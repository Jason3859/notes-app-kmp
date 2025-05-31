package dev.jason.app.compose.multiplatform.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jason.app.compose.multiplatform.database.Notes
import dev.jason.app.compose.multiplatform.database.NotesDao
import kotlinx.coroutines.launch

class NewNoteViewModel(
    private val notesDao: NotesDao
) : ViewModel() {

    fun save(title: String, note: String) {
        val note = Notes(title = title, content = note)

        viewModelScope.launch {
            notesDao.addNote(note)

            println("note id = ${note.id}, title = ${note.title}")
        }
    }

}