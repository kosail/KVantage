package com.korealm.kvantage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.korealm.kvantage.state.KvandClient
import com.korealm.kvantage.ui.App
import dorkbox.systemTray.MenuItem
import dorkbox.systemTray.SystemTray
import kvantage.composeapp.generated.resources.Res
import kvantage.composeapp.generated.resources.favicon
import org.jetbrains.compose.resources.painterResource
import java.net.URL

fun main() = application {
    // This app needs root access to work, but it manages it to escalate it internally and securely.
    // Even though even the daemon watches out this situation to avoid undefined behavior,
    // the app GUI (this one) should NEVER be started as root for obvious security reasons.
    if (isRunningAsRoot()) forbidStartAsRoot(::exitApplication)

    // Launch daemon ONCE and hold reference
    val kvand = remember { KvandClient.getInstance() }
    val icon = painterResource(Res.drawable.favicon)
    var visibility by remember { mutableStateOf(true) }

//    launchTray(
//        clazz = this,
//        onCloseAction = ::exitApplication,
//        onToggleWindow = { visibility = !visibility }
//    )

    if (visibility) {
        Window(
            onCloseRequest = { visibility = !visibility},
            title = "KVantage",
            icon = icon,
            resizable = false,
            state = WindowState( size = DpSize(545.dp, 700.dp) )
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
        "Failed to initialize the backend service.\nRoot permissions are required to run this application.",
        "Critical Error",
        javax.swing.JOptionPane.ERROR_MESSAGE
    )

    exitApp()
}

//fun launchTray(
//    clazz: ApplicationScope,
//    onCloseAction: () -> Unit,
//    onToggleWindow: () -> Unit
//) {
//    val tray = SystemTray.get()
//    tray.setImage(clazz::class.java.getResource("composeResources/drawable/favicon.png"))
//    val menu = tray.menu.apply {
//        add(MenuItem("Show/Hide App") { onToggleWindow() })
//        add(MenuItem("Exit App") { onCloseAction() })
//    }
//}