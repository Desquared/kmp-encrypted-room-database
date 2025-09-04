package com.desquared.encryptedroom.todos

import com.desquared.encryptedroom.db.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getAll(): Flow<List<TodoEntity>>
    suspend fun addTodo(title: String)
    suspend fun deleteTodo(todo: TodoEntity)
}