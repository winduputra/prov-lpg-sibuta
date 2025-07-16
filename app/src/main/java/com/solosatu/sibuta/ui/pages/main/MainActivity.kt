package com.solosatu.sibuta.ui.pages.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.solosatu.sibuta.R
import com.solosatu.sibuta.ui.pages.main.components.bottomNavigation.BottomNavigation
import com.solosatu.sibuta.ui.pages.main.components.bottomNavigation.MainRoutes
import com.solosatu.sibuta.ui.pages.main.components.bottomNavigation.NavigationItem
import com.solosatu.sibuta.ui.pages.main.home.HomePage
import com.solosatu.sibuta.ui.pages.main.meetings.RequestMeetingPage
import com.solosatu.sibuta.ui.pages.main.profile.ProfilePage
import com.solosatu.sibuta.ui.theme.SIBUTATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SIBUTATheme {
                // A surface container using the 'background' color from the theme
                MainView()
            }
        }
    }
}

@Composable
private fun MainView(
    navHostController: NavHostController = rememberNavController()
) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            val menu = listOf(
                NavigationItem(
                    icon = R.drawable.ic_darhboard,
                    route = MainRoutes.Home
                ),
                NavigationItem(
                    icon = R.drawable.ic_home,
                    route = MainRoutes.Request
                ),
                NavigationItem(
                    icon = R.drawable.ic_user_duotone,
                    route = MainRoutes.Account
                )
            )
            if (navBackStackEntry?.destination?.route in menu.map { it.route.path }) BottomNavigation(
                items = menu,
                navHostController = navHostController
            )
        }
    ) { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navHostController,
            startDestination = MainRoutes.Home.path
        ) {
            composable(
                route = MainRoutes.Home.path,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            ) {
                HomePage()
            }
            composable(
                route = MainRoutes.Request.path,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            ) {
                RequestMeetingPage()
            }
            composable(
                route = MainRoutes.Account.path,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            ) {
                ProfilePage()
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    SIBUTATheme {
        MainView()
    }
}
