package com.kodeco.android.countryinfo.dataStore

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesOf
import androidx.datastore.preferences.preferencesDataStore
import com.kodeco.android.countryinfo.dataStore.CountryPrefsImpl.Companion.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject




class CountryPrefsImpl (private val context: Context) : CountryPrefs {
    companion object {
        private const val STORE_NAME = "country_prefs"
        val AUTO_RESTART_SYSTEM_UI = booleanPreferencesKey("auto_restart_system_ui")
        private val Context.dataStore by preferencesDataStore(name = STORE_NAME)

    }
    private val dataStore = context.dataStore


    override fun getLocalStorageEnabled(): Flow<Boolean> {
        return dataStore.data.map { preferences: Preferences ->
            preferences[AUTO_RESTART_SYSTEM_UI] ?: false
        }
    }

    override suspend fun toggleLocalStorage() {
        dataStore.edit { preferences->
            preferences[AUTO_RESTART_SYSTEM_UI]
        }
    }

    override fun getFavoritesFeatureEnabled(): Flow<Boolean> {
        TODO("Not yet implemented")
    }



    override suspend fun toggleFavoritesFeature() {
        TODO("Not yet implemented")
    }
}