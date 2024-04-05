package com.kodeco.android.countryinfo

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kodeco.android.countryinfo.database.CountryDao
import com.kodeco.android.countryinfo.database.CountryDatabase
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.network.adapters.CountryAdapter
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.CountryRepositoryImpl
import com.kodeco.android.countryinfo.ui.nav.CountryInfoNavHost
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {

    private val database: CountryDatabase by lazy {
        CountryDatabase.buildDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val moshi = Moshi.Builder()
            .add(CountryAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val service: CountryService = retrofit.create(CountryService::class.java)
        val repository: CountryRepository = CountryRepositoryImpl(service, database.countryDao())

        setContent {
            MyApplicationTheme {
                CountryInfoNavHost(repository = repository)
            }
        }
    }
}
