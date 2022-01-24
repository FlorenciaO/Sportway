package com.example.sportway.domain.model

data class LoginRequest(
    val appVersion: String,
    val deviceId: String,
    val deviceName: String,
    val deviceModel: String,
    val deviceVersion: String,
    val devicePlatform: String,
    val dimensions: Pair<String, String>,
    val userProfileLanguage: String
)