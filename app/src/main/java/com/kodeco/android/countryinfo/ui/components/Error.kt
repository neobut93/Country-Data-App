package com.kodeco.android.countryinfo.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme

@Composable
fun Error(
    userFriendlyMessageText: String,
    error: Throwable,
    onRetry: (() -> Unit)? = null,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Log.e("CountryErrorScreen", "Error: ${error.message}")
        Text(
            text = userFriendlyMessageText,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )

        onRetry?.let {
            Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 16.dp),
            ) {
                Text(text = stringResource(R.string.error_button_retry_text))
            }
        }
    }
}

@Preview
@Composable
fun ErrorPreview() {
    MyApplicationTheme {
        Error(
            userFriendlyMessageText = "Error message",
            error = Throwable("Error message"),
            onRetry = {},
        )
    }
}
