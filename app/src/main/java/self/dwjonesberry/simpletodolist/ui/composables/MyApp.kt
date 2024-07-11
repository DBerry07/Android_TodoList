package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import self.dwjonesberry.simpletodolist.data.Screens

/**
 * A [Composable] that is a container for every other composable due to the [NavHost]. Includes the
 * list of all possible navigation destinations.
 */
@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.MAIN.name
    ) {
        composable(Screens.MAIN.name) {
            MainLayout(navigateToAddToDoScreen = { navController.navigate(Screens.ADD.name) })
        }
        composable(Screens.ADD.name) {
            AddToDoScreen({ navController.popBackStack() }).Screen
        }
    }

}