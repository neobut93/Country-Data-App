package com.kodeco.android.countryinfo.datastore

import kotlinx.coroutines.flow.Flow

interface CountryPrefs {
    fun getLocalStorageEnabled(): Flow<Boolean>
    fun getFavoritesFeatureEnabled(): Flow<Boolean>

    suspend fun toggleLocalStorage(value: Boolean)
    suspend fun toggleFavoritesFeature(value: Boolean)
}