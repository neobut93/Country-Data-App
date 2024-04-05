package com.kodeco.android.countryinfo.ui.screens.countrylist

import com.kodeco.android.countryinfo.models.Country

sealed class CountryListState {
    data object Loading : CountryListState()
    data class Success(val countries: List<Country>) : CountryListState()
    data class Error(val error: Throwable) : CountryListState()
}
