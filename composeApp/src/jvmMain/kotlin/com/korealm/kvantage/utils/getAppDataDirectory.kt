package com.korealm.kvantage.utils

import java.io.File

private fun getAppDataDirectory(): File {
    val userHome = System.getProperty("user.home")
    val appDir = File(userHome, ".config/kvantage/")
    if (!appDir.exists()) {
        appDir.mkdirs()
    }
    return appDir
}