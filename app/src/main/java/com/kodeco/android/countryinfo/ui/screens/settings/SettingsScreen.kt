package com.kodeco.android.countryinfo.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.datastore.CountryPrefsImpl
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.sample.sampleCountries
import com.kodeco.android.countryinfo.sample.sampleCountry
import com.kodeco.android.countryinfo.ui.screens.CountryListAndSettingsViewModel
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    viewModel: CountryListAndSettingsViewModel,
    onNavigateUp: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val favoritesBoolean by viewModel.getFavorite().collectAsState(initial = true)
    var favoritesToggle by remember { mutableStateOf(favoritesBoolean) }

    val datastoreBoolean by viewModel.getDatabase().collectAsState(initial = true)
    var datastoreToggle by remember { mutableStateOf(datastoreBoolean) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.settings))
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.nav_back_content_description),
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable Favorites Feature",
                    modifier = Modifier.weight(1f),
                )
                Switch(
                    checked = favoritesBoolean,
                    onCheckedChange = {
                        scope.launch {
                            viewModel.setFavorite(it)
                        }
                        favoritesToggle = it
                    },
                    modifier = Modifier.weight(0.3f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable Local Storage",
                    modifier = Modifier.weight(1f),
                )
                Switch(
                    checked = datastoreBoolean,
                    onCheckedChange = {
                        scope.launch {
                            viewModel.setDatabase(it)
                        }
                        datastoreToggle = it
                    },
                    modifier = Modifier.weight(0.3f)
                )
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    val context = LocalContext.current
    MyApplicationTheme {
        SettingsScreen(
            viewModel = CountryListAndSettingsViewModel(
                repository = object : CountryRepository {
                    override val countries: Flow<List<Country>>
                        get() = MutableStateFlow(sampleCountries).asStateFlow()

                    override suspend fun fetchCountries() {}

                    override fun getCountry(index: Int): Country = sampleCountry

                    override suspend fun favorite(country: Country) {}
                },
                prefs = CountryPrefsImpl(context)
            ),
            onNavigateUp = {}
        )
    }
}