package self.dwjonesberry.simpletodolist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import self.dwjonesberry.simpletodolist.screens.AddToDoScreen
import self.dwjonesberry.simpletodolist.screens.MainScreen

@Composable
fun MyApp() {
    val navController = rememberNavController()

    Scaffold { padding ->
        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = Screens.MAIN.name
        ) {
            composable(Screens.MAIN.name) { MainScreen({ navController.navigate(Screens.ADD.name) }).Screen }
            composable(Screens.ADD.name) { AddToDoScreen({ navController.popBackStack() }).Screen }
        }
    }
}