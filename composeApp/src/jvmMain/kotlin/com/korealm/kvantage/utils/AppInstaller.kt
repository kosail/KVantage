package com.korealm.kvantage.utils

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import kotlin.io.path.exists
import kotlin.io.path.outputStream
import kotlin.system.exitProcess

object AppInstaller {
    val flagFile: Path = localKvantageConfig.resolve("preferences.ksl")

    val flagContent: String = "Don't delete this file, as it works as a flag to let know Kvantage that this program has been already run, and that you declined the installation. If you delete it, KVantage will prompt you again to install it on next launch."

    fun isFirstRun(): Boolean {
        val flagExists = localKvantageConfig.exists() && flagFile.exists()
        val diffPath = currentPath != targetPath
        return diffPath && ! flagExists
    }

    fun install() {
        // Create if not exists
        Files.createDirectories(localBin)

        // Copy JAR to .local/bin/kvantage.jar
        Files.copy(currentPath, targetPath, StandardCopyOption.REPLACE_EXISTING)

        // Install .desktop file
        val desktopFileContent = """
            [Desktop Entry]
                Version=1.0
                Type=Application
                Name=KVantage
                Comment=Control your Lenovo laptop battery and performance profiles
                Exec=java -jar $targetPath
                Icon=$iconTarget
                Terminal=false
                Categories=Utility;
        """.trimIndent()

        Files.createDirectories(applicationDir)
        Files.write(applicationDir.resolve("kvantage.desktop"), desktopFileContent.toByteArray())

        // Extract the icon from the JAR into /tmp and then copy it to ~/.local/share/icons/
        val embeddedIcon = this::class.java.classLoader.getResourceAsStream("composeResources/kvantage.composeapp.generated.resources/drawable/favicon.png") ?: throw IOException(
            "Could not find embedded icon at composeResources!"
        )

        embeddedIcon.use { input -> input.copyTo(iconTarget.outputStream()) }

        // Flag is needed in case of using the app as a portable executable
        writeFlag()
    }

    fun writeFlag() { // This is needed, as it will stop Kvantage from asking for installation at every start up
        Files.createDirectories(localKvantageConfig)
        Files.write(flagFile, flagContent.toByteArray())
    }

    fun reRunApp(path: String): Nothing {
        // Re-execute this program but from the new file located at ~/.local/bin
        Runtime.getRuntime().exec(arrayOf("java", "-jar", path))
        exitProcess(0)
    }
}