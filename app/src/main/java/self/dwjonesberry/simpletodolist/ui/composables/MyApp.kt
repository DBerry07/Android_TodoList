package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import self.dwjonesberry.simpletodolist.data.Screens

@OptIn(ExperimentalMaterial3Api::class)
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