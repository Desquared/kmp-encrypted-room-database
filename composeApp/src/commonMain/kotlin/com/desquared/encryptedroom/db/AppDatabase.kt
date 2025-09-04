package com.desquared.encryptedroom.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.SQLiteConnection
import com.desquared.encryptedroom.todos.TodoDao
import com.desquared.encryptedroom.todos.TodoEntity

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "encrypted_room"
const val DATABASE_KEY = "replace-it-with-your-own-key"

@Database(
    version = DATABASE_VERSION,
    entities = [TodoEntity::class]
)

@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): TodoDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

/**
 * Utility: execute a PRAGMA or DDL/DML statement, discarding results.
 *
 * Used internally to apply pragmas like `PRAGMA key`, `PRAGMA journal_mode`, etc.
 */
fun SQLiteConnection.exec(sql: String) {
    val st = prepare(sql)
    try {
        while (st.step()) { /* discard rows */
        }
    } finally {
        st.close()
    }
}