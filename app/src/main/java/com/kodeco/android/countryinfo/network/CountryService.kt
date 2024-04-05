package com.kodeco.android.countryinfo.network

import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.network.adapters.WrappedCountryList
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET("v3.1/all")
    @WrappedCountryList
    suspend fun getAllCountries(): Response<List<Country>>
}
