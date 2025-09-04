package com.desquared.encryptedroom.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey val id: Int,
    val title: String
)