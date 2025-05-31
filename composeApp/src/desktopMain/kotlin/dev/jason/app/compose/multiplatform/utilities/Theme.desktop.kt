package dev.jason.app.compose.multiplatform.utilities

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
private data class DesktopLocalTheme(val theme: Theme)

class DesktopTheme : LocalTheme {

    private val userHome = System.getProperty("user.home")
    private val themesFolder = File(userHome, NOTES_DIR)

    private val themesFile = File(themesFolder, "themes.json")

    private val themesFileExists: Boolean
        get() = themesFile.exists()

    private val json = Json { ignoreUnknownKeys = true }

    init {
        if (!themesFolder.exists()) {
            themesFolder.mkdirs()
        }
        if (!themesFileExists) {
            themesFile.createNewFile()
        }
    }

    override suspend fun getTheme(): Theme {
        return if (themesFileExists) {
            runCatching {
                json.decodeFromString<DesktopLocalTheme>(themesFile.readText()).theme
            }.getOrDefault(Theme.SYSTEM_DEFAULT)
        } else {
            Theme.SYSTEM_DEFAULT
        }
    }

    override suspend fun setTheme(theme: Theme) {
        val localTheme = DesktopLocalTheme(theme)
        themesFile.writeText(json.encodeToString(localTheme))
    }

}