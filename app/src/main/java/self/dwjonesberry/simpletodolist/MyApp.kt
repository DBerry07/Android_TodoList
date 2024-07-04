package self.dwjonesberry.simpletodolist

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import self.dwjonesberry.simpletodolist.screens.AddToDoScreen
import self.dwjonesberry.simpletodolist.screens.MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()

    var currentScreen: Screens by remember { mutableStateOf(Screens.MAIN) }

    @Composable
    fun ChangeAppBar(screen: Screens) {
        if (screen.name == Screens.MAIN.name) {
            MyAppBar(
                navigateToAddScreen = { navController.navigate(Screens.ADD.name) },
                navigateToMainScreen = { navController.navigate(Screens.MAIN.name)} )
                .Composable
        }
    }

    Scaffold(topBar = { ChangeAppBar(screen = currentScreen) }) { padding ->
            NavHost(
                modifier = Modifier.padding(padding),
                navController = navController,
                startDestination = Screens.MAIN.name
            ) {
                composable(Screens.MAIN.name) {
                    currentScreen = Screens.MAIN
                    MainScreen().Screen
                }
                composable(Screens.ADD.name) {
                    currentScreen = Screens.ADD
                    AddToDoScreen({ navController.popBackStack() }).Screen
                }
            }
        }
    }