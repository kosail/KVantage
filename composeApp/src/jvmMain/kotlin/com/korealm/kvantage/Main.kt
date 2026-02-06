package com.korealm.kvantage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.korealm.kvantage.state.KvandClient
import com.korealm.kvantage.ui.App
import com.korealm.kvantage.ui.misc.InstallerDialog
import com.korealm.kvantage.utils.AppInstaller
import kvantage.composeapp.generated.resources.Res
import kvantage.composeapp.generated.resources.favicon
import org.jetbrains.compose.resources.painterResource

fun main() = application {
    // This app needs root access to work, but it manages it to escalate it internally and securely.
    // Even though even the daemon watches out this situation to avoid undefined behavior,
    // the app GUI (this one) should NEVER be started as root for obvious security reasons.
    if (isRunningAsRoot()) forbidStartAsRoot(::exitApplication)

    val icon = painterResource(Res.drawable.favicon)
    var isInstallDialogOpen by remember { mutableStateOf(AppInstaller.isFirstRun()) }

    if (isInstallDialogOpen) {
        Window(
            onCloseRequest = ::exitApplication,
            title = "KVantage",
            icon = icon,
            resizable = false,
            state = WindowState( size = DpSize(530.dp, 360.dp) )
        ) {
            InstallerDialog(
                onDismissRequest = { isInstallDialogOpen = false },
            )
        }
    } else {
        val kvand = remember { KvandClient.getInstance() } // Launch daemon ONCE and hold reference

        Window(
            onCloseRequest = ::exitApplication,
            title = "KVantage",
            icon = icon,
            resizable = false,
            state = WindowState( size = DpSize(545.dp, 850.dp) )
        ) {
            App(kvand)
        }
    }
}

fun isRunningAsRoot(): Boolean {
    return System.getProperty("user.name") == "root" ||
            System.getenv("USER") == "root" ||
            System.getenv("SUDO_UID") != null
}

fun forbidStartAsRoot(exitApp: () -> Unit) {
    javax.swing.JOptionPane.showMessageDialog(
        null,
        "This app needs root access to work, but it manages it to escalate it internally and securely, just for a small portion of it (the backend).\nThe app GUI (the graphical interface) should NEVER be started as root for obvious security reasons.",
        "Critical Error: App must never be started as root",
        javax.swing.JOptionPane.ERROR_MESSAGE
    )

    exitApp()
}