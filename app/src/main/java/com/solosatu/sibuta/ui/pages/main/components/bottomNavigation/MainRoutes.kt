package com.solosatu.sibuta.ui.pages.main.components.bottomNavigation

sealed class MainRoutes(
    val title: String,
    val path: String
) {
    data object Home : MainRoutes("Home", "home")
    data object Request : MainRoutes("Meeting", "meeting")
    data object Account : MainRoutes("Account", "account")
}