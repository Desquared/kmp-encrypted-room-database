package com.desquared.encryptedroom

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform