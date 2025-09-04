package com.desquared.encryptedroom.todos

import com.desquared.encryptedroom.db.AppDatabase
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

class TodoRepositoryImpl(private val db: AppDatabase) : TodoRepository {

    private val dao = db.getDao()

    override fun getAll(): Flow<List<TodoEntity>> = dao.getAll()

    override suspend fun addTodo(title: String) {
        dao.insert(TodoEntity(id = Random.nextInt(), title = title))
    }

    override suspend fun deleteTodo(todo: TodoEntity) {
        dao.delete(todo)
    }
}