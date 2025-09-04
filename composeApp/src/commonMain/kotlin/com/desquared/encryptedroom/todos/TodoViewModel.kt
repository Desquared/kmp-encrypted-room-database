package com.desquared.encryptedroom.todos

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) {
    private val _todos = MutableStateFlow<List<TodoEntity>>(emptyList())
    val todos: StateFlow<List<TodoEntity>> = _todos.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    init {
        scope.launch {
            repository.getAll().collect { _todos.value = it }
        }
    }

    fun addTodo(title: String) {
        scope.launch {
            repository.addTodo(title)
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        scope.launch {
            repository.deleteTodo(todo)
        }
    }

    fun clear() {
        scope.cancel()
    }
}