package com.desquared.encryptedroom

import androidx.compose.ui.window.ComposeUIViewController
import com.desquared.encryptedroom.db.SQLCipherTester
import com.desquared.encryptedroom.db.getDatabase
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val database = getDatabase()

    SQLCipherTester.runChecks() // optional

    return ComposeUIViewController {
        App(database)
    }
}