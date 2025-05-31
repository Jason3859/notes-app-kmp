package dev.jason.app.compose.multiplatform.utilities

import androidx.compose.runtime.MutableState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class AndroidTheme(
    private val dataStore: DataStore<Preferences>,
) : LocalTheme {

    private val themeKey = stringPreferencesKey("theme")

    override suspend fun getTheme(): Theme {
        val preferences = dataStore.data.first()
        val themeName = preferences[themeKey] ?: Theme.SYSTEM_DEFAULT.name
        return Theme.valueOf(themeName)
    }

    override suspend fun setTheme(theme: Theme) {
        dataStore.edit { it[themeKey] = theme.name }
    }
}