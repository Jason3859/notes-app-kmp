package dev.jason.app.compose.multiplatform.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 3)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}