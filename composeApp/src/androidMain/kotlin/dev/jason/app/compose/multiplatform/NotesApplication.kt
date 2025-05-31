package dev.jason.app.compose.multiplatform

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import dev.jason.app.compose.multiplatform.utilities.DATASTORE_FILE
import dev.jason.app.compose.multiplatform.database.NotesDatabase
import okio.Path.Companion.toPath

class NotesApplication : Application() {

    lateinit var database: NotesDatabase
    lateinit var dataStore: DataStore<Preferences>

    override fun onCreate() {
        super.onCreate()
        database = getDatabaseForAndroid()
        dataStore = androidDataStoreInstance()
    }

    private fun getDatabaseForAndroid(): NotesDatabase {
        return Room.databaseBuilder<NotesDatabase>(
            context = this,
            name = "notes.db",
        ).setDriver(BundledSQLiteDriver()).fallbackToDestructiveMigration(false).build()
    }



    private fun androidDataStoreInstance(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                this.filesDir.resolve(DATASTORE_FILE).absolutePath.toPath()
            }
        )
    }

}