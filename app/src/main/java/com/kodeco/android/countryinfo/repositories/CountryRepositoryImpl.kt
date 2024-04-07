package com.kodeco.android.countryinfo.repositories

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.database.CountryDao
import com.kodeco.android.countryinfo.datastore.CountryPrefsImpl
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.ui.screens.countrylist.CountryListViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CountryRepositoryImpl(
    private val service: CountryService,
    private val countryDao: CountryDao,
) : CountryRepository {

    private val _countries: MutableStateFlow<List<Country>> = MutableStateFlow(emptyList())
    override val countries: StateFlow<List<Country>> = _countries.asStateFlow()


    override suspend fun fetchCountries() {
        val favorites = countryDao.getFavoriteCountries()

        try {
        _countries.value = emptyList()

            val countriesResponse = service.getAllCountries()

            countryDao.deleteAllCountries()

            _countries.value =  if (countriesResponse.isSuccessful) {
                val countries = countriesResponse.body()!!
                    .toMutableList()
                    .map { country ->
                        country.copy(isFavorite = favorites.any { it.commonName == country.commonName })
                    }
                countryDao.addCountries(countries)
                countries
            } else {
                throw Throwable("Request failed: ${countriesResponse.message()}")
            }
        } catch (e: Exception) {
            // 1 or 2
            throw Exception("Country data is not available")
            //_countries.value = countryDao.getAllCountries()
        }
    }

    override fun getCountry(index: Int): Country? =
        _countries.value.getOrNull(index)

    override suspend fun favorite(country: Country) {
        val index = _countries.value.indexOf(country)
        val mutableCountries = _countries.value.toMutableList()
        val updatedCountry = country.copy(isFavorite = !country.isFavorite)
        mutableCountries[index] = updatedCountry
        countryDao.updateCountry(updatedCountry)

        _countries.value = mutableCountries.toList()
    }
}
