package com.kodeco.android.countryinfo.ui.screens.countrydetails

import com.kodeco.android.countryinfo.models.Country

sealed class CountryDetailsState {
    data object Loading : CountryDetailsState()
    data class Success(val country: Country) : CountryDetailsState()
    data class Error(val error: Throwable) : CountryDetailsState()
}
