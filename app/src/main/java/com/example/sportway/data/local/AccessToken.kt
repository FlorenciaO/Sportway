package com.example.sportway.data.local

import java.util.*

data class AccessToken(
    val token: String,
    val expiresInMillis: Long
)

fun AccessToken.hasExpired(): Boolean {
    return Date(Date().time + expiresInMillis) < Date()
}
