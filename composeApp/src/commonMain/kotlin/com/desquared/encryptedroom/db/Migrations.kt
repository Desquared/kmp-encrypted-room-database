package com.desquared.encryptedroom.db

import androidx.room.migration.Migration
import androidx.sqlite.SQLiteConnection

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(connection: SQLiteConnection) {
        connection.exec(
            """
            CREATE TABLE IF NOT EXISTS `NoteEntity`(
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `title` TEXT NOT NULL,
                `content` TEXT NOT NULL
            )
        """.trimIndent()
        )
    }
}