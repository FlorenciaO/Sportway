package com.example.sportway.data.remote.dto

data class AccessTokenData(
    val accessToken: String,
    val expiresIn: String,
    val refreshToken: String,
    val tokenType: String,
    val user: Any
)