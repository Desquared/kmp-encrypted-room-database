package com.desquared.encryptedroom.todos

import com.desquared.encryptedroom.db.TodoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoController(private val repository: TodoRepository) {
    private val _todos = MutableStateFlow<List<TodoEntity>>(emptyList())
    val todos: StateFlow<List<TodoEntity>> = _todos.asStateFlow()

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    init {
        scope.launch {
            repository.getAll().collect { list ->
                _todos.value = list
            }
        }
    }

    fun add(title: String) {
        scope.launch {
            repository.addTodo(title)
        }
    }

    fun remove(todo: TodoEntity) {
        scope.launch {
            repository.deleteTodo(todo)
        }
    }

    fun clear() {
        scope.cancel()
    }
}