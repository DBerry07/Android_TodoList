package self.dwjonesberry.simpletodolist

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.MAIN.name) {
        composable(Screens.MAIN.name) { MainScreen() { navController.navigate(Screens.ADD.name)} }
        composable(Screens.ADD.name) { AddTodoScreen() { navController.popBackStack() } }
    }
}