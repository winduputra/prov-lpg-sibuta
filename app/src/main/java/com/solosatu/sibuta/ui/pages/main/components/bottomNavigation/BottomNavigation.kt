package com.solosatu.sibuta.ui.pages.main.components.bottomNavigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.solosatu.sibuta.R
import com.solosatu.sibuta.helper.shadow
import com.solosatu.sibuta.ui.theme.SIBUTATheme


@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    items: List<NavigationItem> = listOf(
        NavigationItem(
            icon = R.drawable.ic_home,
            route = MainRoutes.Home
        )
    ),
    navHostController: NavHostController
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier
            .shadow(
                color = MaterialTheme.colorScheme.primary,
                offsetX = 0.dp,
                offsetY = 4.dp,
                blurRadius = 8.dp
            )
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
    ) {
        items.map { screen ->
            val isSelected = currentRoute == screen.route.path
            val colorTheme =
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline

            NavigationBarItem(
                selected = isSelected,
                label = {
                    Text(screen.route.title, color = colorTheme)
                },
                icon = {
                    Icon(
                        painterResource(id = screen.icon),
                        contentDescription = screen.route.path,
                        tint = colorTheme
                    )
                },
                onClick = {
                    navHostController.navigate(screen.route.path) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    SIBUTATheme {
        BottomNavigation(navHostController = rememberNavController())
    }
}
