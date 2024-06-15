package com.example.plant.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tokenkey")
class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    private val TOKEN_KEY = stringPreferencesKey("token_key")
    private val IS_VALID = booleanPreferencesKey("is_valid")
    private val USER_NAME = stringPreferencesKey("user_name")

    fun getTokenKey(): Flow<String> {
        return dataStore.data.map{preferences->
            preferences[TOKEN_KEY]?:"token_key"
        }
    }

    suspend fun setTokenKey(token_auth: String){
        dataStore.edit { preferences->
            preferences[TOKEN_KEY] = token_auth
        }
    }

    fun getValid(): Flow<Boolean> {
        return dataStore.data.map {preferences->
            preferences[IS_VALID]?: false
        }
    }

    suspend fun setValid(valid:Boolean){
        dataStore.edit {preferences->
            preferences[IS_VALID] = valid
        }
    }
    fun getUserName(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[USER_NAME] ?: "user_name"
        }
    }
    suspend fun setUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = userName
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}