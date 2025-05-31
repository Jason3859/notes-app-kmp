package dev.jason.app.compose.multiplatform.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jason.app.compose.multiplatform.utilities.LocalTheme
import dev.jason.app.compose.multiplatform.utilities.Theme
import dev.jason.app.compose.multiplatform.database.NotesDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val notesDao: NotesDao,
    private val theme: LocalTheme
) : ViewModel() {

    fun getAll() = notesDao.getAllNotes()

    private val _currentTheme = MutableStateFlow(Theme.SYSTEM_DEFAULT)
    val currentTheme = _currentTheme.asStateFlow()

    init {
        viewModelScope.launch {
            _currentTheme.value = theme.getTheme()
        }
    }

    fun setTheme(theme: Theme) {
        _currentTheme.value = theme

        viewModelScope.launch {
            this@HomeViewModel.theme.setTheme(theme)
        }
    }

}