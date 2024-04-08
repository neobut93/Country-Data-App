package com.kodeco.android.countryinfo.ui.screens

sealed interface Screen {
    val path: String

    data object List : Screen {
        override val path = "list"
    }

    data object Details : Screen {
        override val path = "details"
    }

    data object About : Screen {
        override val path = "about"
    }

    data object Settings : Screen {
        override val path = "settings"
    }
}
