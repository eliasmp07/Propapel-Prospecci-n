package org.propapel.prospeccion.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.io.IOException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.propapel.prospeccion.core.data.mappers.toAuthInfo
import org.propapel.prospeccion.core.data.mappers.toAuthInfoSerializable
import org.propapel.prospeccion.core.data.networking.AuthInfoSerializable
import org.propapel.prospeccion.core.domain.AuthInfo
import org.propapel.prospeccion.core.domain.SessionStorage

class SessionStorageImpl(
    private val userPreferences: DataStore<Preferences>
): SessionStorage {
    companion object{
        private val USER_DATA = stringPreferencesKey("user_session")
    }

    override suspend fun set(user: AuthInfo?) {
        withContext(Dispatchers.IO) {
            try {
                userPreferences.edit { preferences ->
                    if (user == null) {
                        preferences[USER_DATA] = ""
                    } else {
                        val userDataJson = Json.encodeToString(user.toAuthInfoSerializable())
                        preferences[USER_DATA] = userDataJson
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            try {
                val preferences = userPreferences.data.firstOrNull() ?: return@withContext null
                val userDataJson = preferences[USER_DATA] ?: return@withContext null
                Json.decodeFromString<AuthInfoSerializable>(userDataJson).toAuthInfo()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
    override fun getUserFlow(): Flow<AuthInfo?> {
        return userPreferences.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences()) // En caso de error de lectura, devolvemos valores vacíos
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val userDataJson = preferences[USER_DATA]
                userDataJson?.takeIf { it.isNotBlank() }?.let {
                    Json.decodeFromString<AuthInfoSerializable>(it).toAuthInfo()
                }
            }
    }
}