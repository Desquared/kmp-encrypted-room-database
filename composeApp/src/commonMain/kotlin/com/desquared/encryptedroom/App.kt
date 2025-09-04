package com.desquared.encryptedroom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.desquared.encryptedroom.todos.TodoController
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(controller: TodoController) {
    val todos by controller.todos.collectAsState()

    TodoListScreen(
        todos = todos,
        onAdd = { controller.add(it) },
        onRemove = { controller.remove(it) }
    )
}

