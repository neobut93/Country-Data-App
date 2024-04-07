package com.kodeco.android.countryinfo.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val STORE_NAME = "country_prefs"
private val Context.dataStore by preferencesDataStore(name = STORE_NAME)

class CountryPrefsImpl @Inject constructor(@ApplicationContext context: Context) : CountryPrefs {

    val favoriteKey = booleanPreferencesKey(STORE_NAME)

    private val dataStore = context.dataStore

    override fun getLocalStorageEnabled(): Flow<Boolean> {
        TODO("Not yet implemented")
    }

    override fun getFavoritesFeatureEnabled(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { pref ->
                val isFavorite = pref[favoriteKey] ?: true
                isFavorite
            }
    }

    override suspend fun toggleLocalStorage() {
        TODO("Not yet implemented")
    }

    override suspend fun toggleFavoritesFeature(value: Boolean) {
        dataStore.edit { pref ->
            pref[favoriteKey] = value
        }
    }
}