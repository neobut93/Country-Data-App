package com.kodeco.android.countryinfo.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kodeco.android.countryinfo.datastore.CountryPrefsImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val prefs: CountryPrefsImpl
) : ViewModel() {

    fun getFavorite(): Flow<Boolean> {
        return prefs.getFavoritesFeatureEnabled()
    }

    fun setFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            prefs.toggleFavoritesFeature(isFavorite)
        }
    }
}