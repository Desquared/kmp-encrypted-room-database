package com.desquared.encryptedroom

import android.app.Application

class DSQApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        System.loadLibrary("sqlcipher")
    }
}