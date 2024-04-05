package com.kodeco.android.countryinfo.network.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryDto(
    val name: CountryNameDto,
    val capital: List<String>?,
    val population: Long,
    val area: Float,
    val flags: CountryFlagsDto,
) {
    val mainCapital = capital?.firstOrNull() ?: "N/A"
    val commonName = name.common
    val flagUrl = flags.png
}
