package com.desquared.encryptedroom.todos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TodoEntity>)

    @Delete
    suspend fun delete(item: TodoEntity)

    @Query("DELETE FROM TodoEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM TodoEntity ORDER BY id DESC")
    fun getAll(): Flow<List<TodoEntity>>

    @Query("SELECT COUNT(*) FROM TodoEntity")
    suspend fun count(): Int

    @Query("SELECT * FROM TodoEntity WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): TodoEntity?
}