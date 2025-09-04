package com.desquared.encryptedroom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.desquared.encryptedroom.db.AppDatabase
import com.desquared.encryptedroom.todos.TodoListScreen
import com.desquared.encryptedroom.todos.TodoRepositoryImpl
import com.desquared.encryptedroom.todos.TodoViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(database: AppDatabase) {
    val viewModel = remember { TodoViewModel(TodoRepositoryImpl(database)) }

    val todos by viewModel.todos.collectAsState()

    TodoListScreen(
        todos = todos,
        onAdd = viewModel::addTodo,
        onRemove = viewModel::deleteTodo
    )
}

