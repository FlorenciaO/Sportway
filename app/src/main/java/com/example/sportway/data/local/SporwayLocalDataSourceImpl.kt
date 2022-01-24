package com.example.sportway.data.local

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SporwayLocalDataSourceImpl @Inject constructor(@ApplicationContext context: Context) :
    SportwayLocalDataSource {

    private companion object {
        const val PREFS_NAME = "my_prefs"
        const val KEY_ACCESS_TOKEN = "accessToken"
    }

    private val preferences by lazy {
        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )
    }
    private val gson = Gson()

    override fun getAccessToken(): AccessToken? {
        val accessToken: AccessToken? =
            gson.fromJson(preferences.getString(KEY_ACCESS_TOKEN, null), AccessToken::class.java)
        return accessToken?.takeIf { it.hasExpired() }
    }

    override fun saveAccessToken(accessToken: AccessToken) {
        with(preferences.edit()) {
            putString(KEY_ACCESS_TOKEN, gson.toJson(accessToken))
            apply()
        }
    }

}