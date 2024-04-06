package com.kodeco.android.countryinfo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kodeco.android.countryinfo.models.Country

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCountry(country: Country)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCountries(countries: List<Country>)

    @Query("SELECT * FROM countries")
    suspend fun getAllCountries() : List<Country>

    @Query("SELECT * FROM countries WHERE commonName = :commonName")
    suspend fun getCountry(commonName: String): Country

    @Delete
    suspend fun deleteCountry(country: Country)

    @Query("UPDATE countries SET isFavorite = :isFavorite WHERE commonName = :commonName")
    suspend fun setFavourite(isFavorite: Boolean, commonName: String)
}