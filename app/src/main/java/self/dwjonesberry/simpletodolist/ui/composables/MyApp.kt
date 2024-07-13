package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import self.dwjonesberry.simpletodolist.data.FirebaseRepository
import self.dwjonesberry.simpletodolist.data.Screens
import self.dwjonesberry.simpletodolist.data.TodoItem
import self.dwjonesberry.simpletodolist.data.TodoViewModel

/**
 * A [Composable] that is a container for every other composable due to the [NavHost]. Includes the
 * list of all possible navigation destinations.
 */
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val todoViewModel: TodoViewModel = TodoViewModel(repo = FirebaseRepository())

    NavHost(
        navController = navController,
        startDestination = Screens.MAIN.name
    ) {
        composable(Screens.MAIN.name) {
            MainLayout(
                viewModel = todoViewModel,
                navigateToAddToDoScreen = { todoItem: TodoItem? ->
                    todoViewModel.selectedTodo = todoItem
                    navController.navigate(Screens.ADD.name) })
        }
        composable(Screens.ADD.name) {
            AddTodoLayout(
                viewModel = todoViewModel,
                navigateToMainScreen = { navController.popBackStack() })
        }
    }

}