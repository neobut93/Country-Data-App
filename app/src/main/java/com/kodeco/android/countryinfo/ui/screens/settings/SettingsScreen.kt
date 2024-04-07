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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.screens.countrylist.CountryListViewModel
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    viewModel: CountryListViewModel,
    onNavigateUp: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val flow by viewModel.getFavorite().collectAsState(initial = false)
    var checked by remember { mutableStateOf(flow) }

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
                    text = "Enable Local Storage",
                    modifier = Modifier.weight(1f),
                )
                Switch(
                    checked = flow,
                    onCheckedChange = {
                        scope.launch {
                            viewModel.setFavorite(it)
                        }
                        checked = it
                    },
                    modifier = Modifier.weight(0.3f)
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun SettingsScreenPreview() {
//    MyApplicationTheme {
//        SettingsScreen { }
//    }
//}