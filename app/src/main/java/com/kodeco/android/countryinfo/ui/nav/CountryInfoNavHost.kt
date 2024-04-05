package com.kodeco.android.countryinfo.ui.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.repositories.CountryRepository
import com.kodeco.android.countryinfo.ui.screens.Screen
import com.kodeco.android.countryinfo.ui.screens.about.AboutScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModel
import com.kodeco.android.countryinfo.ui.screens.countrylist.CountryListScreen
import com.kodeco.android.countryinfo.ui.screens.countrylist.CountryListViewModel

@Composable
fun CountryInfoNavHost(
    repository: CountryRepository,
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.List.path) {
        composable(Screen.List.path) {
            CountryListScreen(
                viewModel = viewModel(
                    factory = CountryListViewModel.CountryInfoViewModelFactory(
                        repository = repository,
                    ),
                ),
                onCountryRowTap = { countryIndex ->
                    navController.navigate("${Screen.Details.path}/$countryIndex")
                },
                onAboutTap = { navController.navigate(Screen.About.path) },
            )
        }

        composable(
            route = "${Screen.Details.path}/{countryIndex}",
            arguments = listOf(navArgument("countryIndex") { type = NavType.IntType }),
        ) { backStackEntry ->
            val countryIndex = backStackEntry.arguments!!.getInt("countryIndex")
            CountryDetailsScreen(
                countryIndex = countryIndex,
                viewModel = viewModel(
                    factory = CountryDetailsViewModel.CountryDetailsViewModelFactory(
                        repository = repository,
                    ),
                ),
                onNavigateUp = { navController.navigateUp() },
            )
        }

        composable(Screen.About.path) {
            AboutScreen(
                onNavigateUp = { navController.navigateUp() },
            )
        }
    }
}
