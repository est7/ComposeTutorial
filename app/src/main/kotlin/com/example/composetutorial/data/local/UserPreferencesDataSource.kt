package com.example.composetutorial.data.local

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.composetutorial.data.dto.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferencesDataSource(private val androidContext: Context) {
    companion object {
        private const val USER_PREFERENCES_NAME = "user_preferences"

        private object PreferencesKeys {
            val TOKEN: Preferences.Key<String> = stringPreferencesKey("token")
            val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
        }
    }

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    private val dataStore = androidContext.dataStore

    val userPreferencesFlow: Flow<UserPreferences> = dataStore.data.map { preferences ->
        val showCompleted = preferences[PreferencesKeys.SHOW_COMPLETED] ?: false
        UserPreferences(showCompleted)
    }

    suspend fun updateShowCompleted(showCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHOW_COMPLETED] = showCompleted
        }
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.TOKEN]
    }

    suspend fun saveToken(token: String): Result<Boolean> {
        return kotlin.runCatching {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.TOKEN] = token
            }
            true
        }
    }

    suspend fun clearToken(): Result<Boolean> {
        return kotlin.runCatching {
            dataStore.edit { preferences ->
                preferences.remove(PreferencesKeys.TOKEN)
            }
            true
        }
    }
}