package com.kodeco.android.countryinfo.di

import android.content.Context
import com.kodeco.android.countryinfo.database.CountryDatabase
import com.kodeco.android.countryinfo.network.CountryService
import com.kodeco.android.countryinfo.network.adapters.CountryAdapter
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.repositories.CountryRepositoryImpl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CountryInfoSingletonModule {
    @Provides
    @Singleton
    fun provideCountryService(): CountryService {
        val moshi = Moshi.Builder()
            .add(CountryAdapter())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(CountryService::class.java)
    }

    @Provides
    @Singleton
    fun provideCountryDatabase(@ApplicationContext applicationContext: Context): CountryDatabase {
        return CountryDatabase.buildDatabase(applicationContext)
    }

    @Provides
    @Singleton
    fun providesCountryRepository(
        service: CountryService,
        database: CountryDatabase,
    ): CountryRepository = CountryRepositoryImpl(service, database.countryDao())
}
