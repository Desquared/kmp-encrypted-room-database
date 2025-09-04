package com.desquared.encryptedroom.todos

import com.desquared.encryptedroom.db.TodoEntity
import com.desquared.encryptedroom.db.getDatabase
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

class IosTodoRepository : TodoRepository {
    private val dao = getDatabase().getDao()

    override fun getAll(): Flow<List<TodoEntity>> = dao.getAll()

    override suspend fun addTodo(title: String) {
        dao.insert(TodoEntity(id = Random.nextInt(), title = title))
    }

    override suspend fun deleteTodo(todo: TodoEntity) {
        dao.delete(todo)
    }
}