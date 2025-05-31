package dev.jason.app.compose.multiplatform.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert
    suspend fun addNote(notes: Notes)

    @Update
    suspend fun updateNote(notes: Notes): Int

    @Delete
    suspend fun deleteNote(notes: Notes)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Int): Flow<Notes>

    @Query("SELECT * FROM notes ORDER BY title ASC")
    fun getAllNotes(): Flow<List<Notes>>

}