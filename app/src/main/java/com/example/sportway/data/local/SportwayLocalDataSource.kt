package com.example.sportway.data.local

interface SportwayLocalDataSource {
    fun getAccessToken(): AccessToken?
    fun saveAccessToken(accessToken: AccessToken)
}