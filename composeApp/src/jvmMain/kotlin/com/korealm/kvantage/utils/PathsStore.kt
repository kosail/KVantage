package com.korealm.kvantage.utils

import java.nio.file.Path
import java.nio.file.Paths

val home: String = System.getProperty("user.home")
val localBin: Path = Paths.get(home, ".local", "bin")
val applicationDir: Path = Paths.get(home, ".local", "share", "applications")

val iconDir: Path = Paths.get(home, ".local", "share", "icons")
val iconTarget: Path = iconDir.resolve("kvantage.png")

val currentPath: Path = Paths.get(AppInstaller::class.java.protectionDomain.codeSource.location.toURI())
val targetPath: Path = localBin.resolve("kvantage")

val localKvantageConfig: Path = Paths.get(home, ".config", "kvantage")
val logsDir: Path = Paths.get(localKvantageConfig.toString(), "logs")