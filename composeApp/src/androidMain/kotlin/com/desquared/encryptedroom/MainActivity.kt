package com.desquared.encryptedroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.desquared.encryptedroom.todos.AndroidTodoRepository
import com.desquared.encryptedroom.todos.TodoController

class MainActivity : ComponentActivity() {
    private val controller by lazy {
        TodoController(AndroidTodoRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(controller)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}