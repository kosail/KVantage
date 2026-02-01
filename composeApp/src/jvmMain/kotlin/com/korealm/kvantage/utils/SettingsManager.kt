package com.korealm.kvantage.utils

import com.korealm.kvantage.models.Settings
import kotlinx.serialization.json.Json
import java.io.File

object SettingsManager {
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
    }

    private val settingsFile: File = File(System.getProperty("user.home"), ".config/Kvantage/models.json")

    fun loadSettings(): Settings {
        return if (settingsFile.exists()) {
            try {
                val text = settingsFile.readText()
                json.decodeFromString<Settings>(text)

            } catch (e: Exception) {
                println("[SettingsManager]: Failed to load models from disk. Exception info: ${e.message}")
                Settings() // Fallback to default models to start the app
            }
        } else {
            println("[SettingsManager]: No models file found on disk. Loading defaults.")
            Settings() // If the file doesn't exist, then load the default models
        }
    }

    fun saveSettings(settings: Settings) {
        try {
            settingsFile.parentFile.mkdirs()
            settingsFile.writeText(json.encodeToString(settings))

            println("[SettingsManager]: Settings saved to disk!")
        } catch (e: Exception) {
            println("[SettingsManager]: Failed to save models to disk. Exception info: ${e.message}")
        }
    }
}