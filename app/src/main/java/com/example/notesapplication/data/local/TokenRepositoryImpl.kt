package com.example.notesapplication.data.local

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TokenRepository {

    private val sharedPref = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    override suspend fun saveToken(token: String) {
        sharedPref.edit { putString("auth_token", token) }
    }

    override suspend fun getToken(): String? {
        return sharedPref.getString("auth_token", null)
    }
}
