package com.desquared.encryptedroom.todos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey val id: Int,
    val title: String
)