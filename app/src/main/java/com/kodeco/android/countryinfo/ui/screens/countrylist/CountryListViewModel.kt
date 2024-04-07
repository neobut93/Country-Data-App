package com.kodeco.android.countryinfo.ui.screens.countrylist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.datastore.CountryPrefsImpl
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val repository: CountryRepository,
    private val prefs: CountryPrefsImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountryListState>(CountryListState.Loading)

    val uiState: StateFlow<CountryListState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository
                .countries
                .catch {
                    _uiState.value = CountryListState.Error(it)
                }
                .collect {
                    _uiState.value = CountryListState.Success(it)
                }
        }

        fetchCountries()
    }

    fun fetchCountries() {
        _uiState.value = CountryListState.Loading

        viewModelScope.launch {
            try {
                repository.fetchCountries()
            } catch (e: Exception) {
                _uiState.value = CountryListState.Error(e)
            }
        }
    }

    fun favorite(country: Country) {
        viewModelScope.launch {
            repository.favorite(country)
        }
    }

    fun getFavorite(): Flow<Boolean> {
        return prefs.getFavoritesFeatureEnabled()
    }

    fun setFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            prefs.toggleFavoritesFeature(isFavorite)
        }
    }

    fun getDatabase(): Flow<Boolean> {
        return prefs.getLocalStorageEnabled()
    }

    fun setDatabase(isDatabase: Boolean) {
        viewModelScope.launch {
            prefs.toggleLocalStorage(isDatabase)
        }
    }
}
