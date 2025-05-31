package dev.jason.app.compose.multiplatform.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jason.app.compose.multiplatform.database.Notes
import dev.jason.app.compose.multiplatform.database.NotesDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NoteEditViewModel(
    private val notesDao: NotesDao
) : ViewModel() {

    fun update(
        id: Int,
        title: String,
        content: String
    ) {
        viewModelScope.launch {

            val existingNote = getNoteById(id)

            val updatedNote = existingNote.copy(
                title = title, content = content
            )

            notesDao.updateNote(updatedNote)
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            val note = getNoteById(id)
            notesDao.deleteNote(note)
        }
    }

    private suspend fun getNoteById(id: Int): Notes {
        return notesDao.getNoteById(id).first()
    }

}