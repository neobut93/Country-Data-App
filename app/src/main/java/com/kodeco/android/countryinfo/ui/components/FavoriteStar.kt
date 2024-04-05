package com.kodeco.android.countryinfo.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kodeco.android.countryinfo.R
import com.kodeco.android.countryinfo.models.Country
import com.kodeco.android.countryinfo.sample.sampleCountry
import com.kodeco.android.countryinfo.ui.theme.Gold

@Composable
fun FavoriteStar(country: Country, onTap: () -> Unit) {
    val favoriteTransition = updateTransition(
        targetState = country.isFavorite,
        label = "${country.commonName}_favorite_transition",
    )
    val scaleAnimation by favoriteTransition.animateFloat(
        transitionSpec = {
            if (!country.isFavorite) tween(0) else {
                keyframes {
                    durationMillis = 1_000
                    1.0f at 0 using FastOutSlowInEasing
                    0.75f at 400 using FastOutSlowInEasing
                    1.5f at 700 using FastOutSlowInEasing
                    1.0f at 1_000 using FastOutSlowInEasing
                }
            }
        },
        label = "${country.commonName}_favorite_size",
    ) { state ->
        if (state) {
            1.0f
        } else {
            1.0f
        }
    }
    val colorAnimation by favoriteTransition.animateColor(
        transitionSpec = {
            if (!country.isFavorite) tween(0) else {
                keyframes {
                    durationMillis = 1_000
                    Gold at 700 using FastOutSlowInEasing
                    Color.Yellow at 1_000 using FastOutSlowInEasing
                }
            }
        },
        label = "${country.commonName}_favorite_color",
    ) { state ->
        if (state) {
            Color.Yellow
        } else {
            LocalContentColor.current
        }
    }
    val rotationAnimation by favoriteTransition.animateFloat(
        transitionSpec = { tween(if (!country.isFavorite) 0 else 750) },
        label = "${country.commonName}_favorite_rotation",
    ) { state ->
        if (state) {
            360.0f
        } else {
            0.0f
        }
    }
    Crossfade(
        targetState = country.isFavorite,
        animationSpec = tween(500),
        modifier = Modifier.padding(all = 8.dp),
        label = "${country.commonName}_favorite_crossfade",
    ) { state ->
        IconButton(onClick = onTap) {
            Icon(
                painter = painterResource(
                    id = if (state) {
                        R.drawable.star_filled
                    } else {
                        R.drawable.star_outline
                    },
                ),
                contentDescription = stringResource(R.string.favorite),
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(32.dp)
                    .graphicsLayer(
                        scaleX = scaleAnimation,
                        scaleY = scaleAnimation,
                        rotationZ = rotationAnimation,
                    ),
                tint = colorAnimation,
            )
        }
    }
}

@Preview
@Composable
fun FavoriteStarPreview() {
    var country by remember { mutableStateOf(sampleCountry) }
    FavoriteStar(
        country = country,
        onTap = {
            country = country.copy(isFavorite = !country.isFavorite)
        },
    )
}
