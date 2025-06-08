package com.korealm.kvantage.settings

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

object SettingsManager {
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }
    private val settingsFile: File = File(System.getProperty("user.home"), ".config/Kvantage/settings.json")

    fun loadSettings(): Settings {
        return if (settingsFile.exists()) {
            try {
                val text = settingsFile.readText()
                json.decodeFromString<Settings>(text)

            } catch (e: Exception) {
                println("[SettingsManager]: Failed to load settings from disk. Exception info: ${e.message}")
                Settings() // Fallback to default settings to start the app
            }
        } else {
            println("[SettingsManager]: No settings file found on disk. Loading defaults.")
            Settings() // If the file doesn't exist, then load the default settings
        }
    }

    fun saveSettings(settings: Settings) {
        try {
            settingsFile.parentFile.mkdirs()
            settingsFile.writeText(json.encodeToString(settings))

            println("[SettingsManager]: Settings saved to disk!")
        } catch (e: Exception) {
            println("[SettingsManager]: Failed to save settings to disk. Exception info: ${e.message}")
        }
    }
}