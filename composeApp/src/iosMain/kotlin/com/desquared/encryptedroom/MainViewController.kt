package com.desquared.encryptedroom

import androidx.compose.ui.window.ComposeUIViewController
import com.desquared.encryptedroom.db.SQLCipherTester
import com.desquared.encryptedroom.todos.IosTodoRepository
import com.desquared.encryptedroom.todos.TodoController
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val repository = IosTodoRepository() // Pass the db if needed
    val controller = TodoController(repository)

    SQLCipherTester.runChecks()

    return ComposeUIViewController {
        App(controller)
    }
}