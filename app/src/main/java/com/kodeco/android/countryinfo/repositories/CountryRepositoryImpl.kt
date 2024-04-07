package com.kodeco.android.countryinfo.repositories

import androidx.lifecycle.asLiveData
import com.kodeco.android.countryinfo.database.CountryDao
import com.kodeco.android.countryinfo.datastore.CountryPrefsImpl
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.network.CountryService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CountryRepositoryImpl(
    private val service: CountryService,
    private val countryDao: CountryDao,
    private val prefs: CountryPrefsImpl,
    ) : CountryRepository {

    private val _countries: MutableStateFlow<List<Country>> = MutableStateFlow(emptyList())
    override val countries: StateFlow<List<Country>> = _countries.asStateFlow()
    private var databaseState = prefs.getLocalStorageEnabled().asLiveData()

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

            if(databaseState.value == true) {
                _countries.value = countryDao.getAllCountries()
            } else {
                throw Exception("Country data is not available")
            }
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
