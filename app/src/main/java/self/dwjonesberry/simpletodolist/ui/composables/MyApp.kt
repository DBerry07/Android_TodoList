package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import self.dwjonesberry.simpletodolist.data.FirebaseRepository
import self.dwjonesberry.simpletodolist.data.Screens
import self.dwjonesberry.simpletodolist.data.TaskViewModel
import self.dwjonesberry.simpletodolist.ui.composables.add.AddTaskScreen
import self.dwjonesberry.simpletodolist.ui.composables.main.MainLayout

/**
 * A [Composable] that is a container for every other composable due to the [NavHost]. Includes the
 * list of all possible navigation destinations.
 */
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val taskViewModel: TaskViewModel = TaskViewModel(repo = FirebaseRepository())

    taskViewModel.navigateToMainScreen = {
        navController.popBackStack()
    }
    taskViewModel.navigateToAddScreenWithArguments = { myTask ->
        taskViewModel.selectedTodo = myTask
        navController.navigate(Screens.ADD.name)
    }
    taskViewModel.popBackStack = {
        navController.popBackStack()
    }

    NavHost(
        navController = navController,
        startDestination = Screens.MAIN.name
    ) {
        composable(Screens.MAIN.name) {
            MainLayout(
                viewModel = taskViewModel)
        }
        composable(Screens.ADD.name) {
            AddTaskScreen(
                viewModel = taskViewModel,
            )
        }
    }

}