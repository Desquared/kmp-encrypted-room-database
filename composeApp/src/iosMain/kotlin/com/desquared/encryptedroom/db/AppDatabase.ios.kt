package com.desquared.encryptedroom.db

import androidx.room.Room
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

fun getDatabase(): AppDatabase {
    val dbFilePath = documentDirectory() + "/${DATABASE_NAME}"
    val passphrase = DATABASE_KEY
    return Room.databaseBuilder<AppDatabase>(name = dbFilePath)
        .setDriver(SQLCipherNativeDriver(passphrase = passphrase, enableWal = true))
        .build()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val url: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(url?.path)
}