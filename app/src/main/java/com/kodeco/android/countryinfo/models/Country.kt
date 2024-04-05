package com.kodeco.android.countryinfo.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "countries")
data class Country(
    @PrimaryKey
    val commonName: String,
    val mainCapital: String,
    val population: Long,
    val area: Float,
    val flagUrl: String,
    val isFavorite: Boolean = false,
) : Parcelable
