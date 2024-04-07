package com.kodeco.android.countryinfo.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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

// create object
class CountryPrefsImpl @Inject constructor(@ApplicationContext context: Context) : CountryPrefs {

//    companion object {
//        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settingPrefs")
//        val favoriteKey = booleanPreferencesKey("FAVORITE_KEY")
//    }

    // move to constants or leave here
    val favoriteKey = booleanPreferencesKey("FAVORITE_KEY")

    private val Context.dataStore by preferencesDataStore(name = "settingPrefs")
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