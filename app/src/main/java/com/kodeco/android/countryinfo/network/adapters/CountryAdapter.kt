package com.kodeco.android.countryinfo.network.adapters

import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.network.dto.CountryDto
import com.kodeco.android.countryinfo.network.dto.CountryFlagsDto
import com.kodeco.android.countryinfo.network.dto.CountryNameDto
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class WrappedCountryList

class CountryAdapter {
    @WrappedCountryList
    @FromJson
    fun fromJson(countryDtoList: List<CountryDto>) : List<Country> = countryDtoList.map { countryDto ->
        Country(
            commonName = countryDto.commonName,
            mainCapital = countryDto.mainCapital,
            population = countryDto.population,
            area = countryDto.area,
            flagUrl = countryDto.flagUrl,
        )
    }

    @ToJson
    fun toJson(@WrappedCountryList countryList: List<Country>): List<CountryDto> = countryList.map { country ->
        CountryDto(
            name = CountryNameDto(common = country.commonName),
            capital = listOf(country.mainCapital),
            population = country.population,
            area = country.area,
            flags = CountryFlagsDto(png = country.flagUrl, svg = ""),
        )
    }
}
