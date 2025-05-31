package dev.jason.app.compose.multiplatform.utilities

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.jason.app.compose.multiplatform.database.NotesDatabase
import dev.jason.app.compose.multiplatform.viewmodel.HomeViewModel
import dev.jason.app.compose.multiplatform.viewmodel.NewNoteViewModel
import dev.jason.app.compose.multiplatform.viewmodel.NoteEditViewModel
import java.io.File

const val NOTES_DIR = ".notes-kmp"

private val notesDao = getDatabaseForDesktop().notesDao()

actual val newNoteViewModel: NewNoteViewModel
    get() = NewNoteViewModel(
        notesDao = notesDao
    )

actual val noteEditViewModel: NoteEditViewModel
    get() = NoteEditViewModel(
        notesDao = notesDao
    )

actual val homeViewModel: HomeViewModel
    get() = HomeViewModel(
        notesDao = notesDao,
        theme = DesktopTheme()
    )

private fun getDatabaseForDesktop(): NotesDatabase {
    return Room.databaseBuilder<NotesDatabase>(
        name = getDbPath()
    )
        .setDriver(BundledSQLiteDriver())
        .fallbackToDestructiveMigration(false)
        .build()
}
private const val NOTES_DB_FILE = "notes.db"

private fun getDbPath(): String {
    val userHome = System.getProperty("user.home")
        ?: System.getenv("USERPROFILE")
        ?: System.getenv("HOME")
        ?: throw IllegalStateException("User dir not found")

    val dbDir = File(userHome, NOTES_DIR)
    if (!dbDir.exists()) {
        dbDir.mkdirs()
    }

    val dbFile = File(dbDir, NOTES_DB_FILE)
    return dbFile.absolutePath
}