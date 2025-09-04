package com.desquared.encryptedroom.db

import android.content.Context
import androidx.room.Room
import net.zetetic.database.sqlcipher.SupportOpenHelperFactory

fun getDatabase(context: Context): AppDatabase {
    val passphrase: ByteArray = DATABASE_KEY.toByteArray(Charsets.UTF_8)
    val factory = SupportOpenHelperFactory(passphrase)
    return Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    )
        .openHelperFactory(factory)
        .build()
}