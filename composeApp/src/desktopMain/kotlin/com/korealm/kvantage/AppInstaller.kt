package com.korealm.kvantage

import AnimatedColorfulBackground
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.korealm.kvantage.AppInstaller.currentPath
import com.korealm.kvantage.AppInstaller.declinedInstall
import com.korealm.kvantage.AppInstaller.targetPath
import com.korealm.kvantage.ui.theme.GruvboxDarkText
import com.korealm.kvantage.ui.theme.GruvboxLightPrimary
import com.korealm.kvantage.ui.theme.GruvboxLightSecondary
import com.korealm.kvantage.ui.theme.GruvboxLightTertiary
import kvantage.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.io.path.exists
import kotlin.io.path.outputStream
import kotlin.system.exitProcess

object AppInstaller {
    val home = System.getProperty("user.home")
    val localBin = Paths.get(home, ".local", "bin")
    val applicationDir = Paths.get(home, ".local", "share", "applications")

    val iconDir = Paths.get(home, ".local", "share", "icons")
    val iconTarget = iconDir.resolve("kvantage.png")

    val currentPath = Paths.get(AppInstaller::class.java.protectionDomain.codeSource.location.toURI())
    val targetPath = localBin.resolve("kvantage")

    val localKvantageConfig = Paths.get(home, ".config", "Kvantage")
    val flagFile = localKvantageConfig.resolve("preferences.ksl")

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
        val embeddedIcon = this::class.java.classLoader.getResourceAsStream("composeResources/kvantage.composeapp.generated.resources/drawable/favicon.png") ?: throw IOException("Could not find embedded icon at composeResources!")

        embeddedIcon.use { input -> input.copyTo(iconTarget.outputStream()) }
    }

    fun declinedInstall() {
        val flagContent = "Don't delete this file, as it works as a flag to let know Kvantage that this program has been already run, and that you declined installation forever. If you delete it, KVantage will prompt you again to install it on next launch."
        Files.createDirectories(localKvantageConfig)
        Files.write(flagFile, flagContent.toByteArray())
    }

    fun reRunApp(path: String): Nothing {
        // Re-execute this program but from the new file located at ~/.local/bin
        Runtime.getRuntime().exec(arrayOf("java", "-jar", path))
        exitProcess(0)
    }
}

@Composable
fun InstallDialog(
    onDismissRequest: () -> Unit
) {
    var showConfirmation by remember { mutableStateOf(false) }
    var userChoice by remember { mutableStateOf<Boolean?>(null) } // Track choice

    Dialog(
        onDismissRequest = {
            if (showConfirmation) onDismissRequest() // Only close after confirmation
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        AnimatedColorfulBackground(modifier = Modifier.fillMaxSize().blur(200.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .size(width = 450.dp, height = 330.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .background(color = Color.Transparent)
            ) {
                when {
                    // Initial screen
                    !showConfirmation -> {
                        Text(
                            text = stringResource(Res.string.ask_install).format("\n", "\n\n"),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 30.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
                        ) {
                            Button(
                                onClick = {
                                    AppInstaller.install()
                                    showConfirmation = true
                                    userChoice = true
                                },
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GruvboxLightPrimary,
                                    contentColor = GruvboxDarkText
                                ),
                                modifier = Modifier
                            ) { Text(text = stringResource(Res.string.okay)) }

                            Button(
                                onClick = { onDismissRequest() },
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GruvboxLightSecondary,
                                    contentColor = GruvboxDarkText
                                ),
                                modifier = Modifier
                            ) { Text(text = stringResource(Res.string.no)) }

                            Button(
                                onClick = {
                                    declinedInstall()
                                    showConfirmation = true
                                    userChoice = false
                                },
                                shape = MaterialTheme.shapes.medium,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GruvboxLightTertiary,
                                    contentColor = GruvboxDarkText
                                ),
                                modifier = Modifier
                            ) { Text(text = stringResource(Res.string.dont_ask)) }
                        }
                    }

                    // Confirmation screen
                    showConfirmation -> {
                        Text(
                            text = when {
                                userChoice == true -> stringResource(Res.string.success_install)
                                else -> stringResource(Res.string.declined_forever_install)
                            },
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 30.dp)
                        )

                        Button(
                            onClick = { if (userChoice == true) AppInstaller.reRunApp(targetPath.toString()) else onDismissRequest() },
                            shape = MaterialTheme.shapes.medium,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GruvboxLightTertiary,
                                contentColor = GruvboxDarkText
                            ),
                            modifier = Modifier
                        ) { Text(text = stringResource(Res.string.okay)) }
                    }
                }
            }
        }
    }
}