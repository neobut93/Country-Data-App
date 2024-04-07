package com.kodeco.android.countryinfo.ui.screens.about

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import android.widget.ToggleButton
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.kodeco.android.countryinfo.BuildConfig
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.dataStore.CountryPrefsImpl
import com.kodeco.android.countryinfo.ui.screens.countrylist.CountryListViewModel
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

@Composable
fun AboutScreen(
    onNavigateUp: () -> Unit,
    viewModel: CountryListViewModel,
    ) {
    var checked by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val data = CountryPrefsImpl(context)

    val savedStatus = data.getLocalStorageEnabled().collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.about_screen_title))
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Text(
                text = stringResource(id = R.string.about_screen_content),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )

            Text(
                text = stringResource(id = R.string.about_screen_version, BuildConfig.VERSION_NAME),
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
            )
            Switch(
                checked = checked,
                onCheckedChange = {
                    scope.launch {
                        data.toggleLocalStorage()
                    }
                    checked = it
                }
            )
            Text(text = savedStatus.value.toString())
        }
    }
}

//@Preview(uiMode = UI_MODE_NIGHT_YES)
//@Preview(uiMode = UI_MODE_NIGHT_NO)
//@Composable
//fun AboutScreenPreview() {
//    MyApplicationTheme {
//        AboutScreen { }
//    }
//}
