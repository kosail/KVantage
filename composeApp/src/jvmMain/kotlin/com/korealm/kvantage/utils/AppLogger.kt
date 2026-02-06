package com.korealm.kvantage.utils

import java.nio.file.Files
import java.util.logging.*
import kotlin.io.path.absolutePathString

object AppLogger {
    // Single JVM logger instance
    private val logger: Logger = Logger.getLogger("KVantage").apply {
        useParentHandlers = false // Prevent double console logs (root logger already has one)
        level = Level.ALL
    }

    // Lazy init so the disk is NOT touched until the first log
    @Volatile
    private var initialized = false


    // Called only when the first log happens
    private fun ensureInit() {
        if (initialized) return

        synchronized(this) {
            if (initialized) return

            Files.createDirectories(logsDir)

            val fileHandler = FileHandler(
                logsDir.resolve("kvantage.%g.log").absolutePathString(),
                1_000_000, // 1MB
                5,         // rotation count
                true
            ).apply {
                formatter = SimpleFormatter()
                level = Level.FINE
            }

            val consoleHandler = ConsoleHandler().apply {
                formatter = SimpleFormatter()
                level = Level.FINE
            }

            logger.addHandler(fileHandler)
            logger.addHandler(consoleHandler)

            initialized = true
        }
    }


    fun info(tag: String, message: String) {
        ensureInit()
        logger.info("[$tag] $message")
    }

    fun debug(tag: String, message: String) {
        ensureInit()
        logger.fine("[$tag] $message")
    }

    fun error(tag: String, message: String, throwable: Throwable? = null) {
        ensureInit()
        logger.log(Level.SEVERE, "[$tag] $message", throwable)
    }
}