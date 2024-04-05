package com.kodeco.android.countryinfo.repositories

import com.kodeco.android.countryinfo.models.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    val countries: Flow<List<Country>>

    suspend fun fetchCountries()
    fun getCountry(index: Int): Country?
    suspend fun favorite(country: Country)
}
