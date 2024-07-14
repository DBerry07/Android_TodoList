package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import self.dwjonesberry.simpletodolist.data.FirebaseRepository
import self.dwjonesberry.simpletodolist.data.Screens
import self.dwjonesberry.simpletodolist.data.MyTask
import self.dwjonesberry.simpletodolist.data.TaskViewModel

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
    taskViewModel.navigateToAddScreen = { myTask ->
        taskViewModel.selectedTodo = myTask
        navController.navigate(Screens.ADD.name)
    }

    NavHost(
        navController = navController,
        startDestination = Screens.MAIN.name
    ) {
        composable(Screens.MAIN.name) {
            MainLayout(
                viewModel = taskViewModel,
                navigateToAddToDoScreen = { myTask: MyTask? ->
                    taskViewModel.selectedTodo = myTask
                    navController.navigate(Screens.ADD.name) })
        }
        composable(Screens.ADD.name) {
            AddTaskScreen(
                viewModel = taskViewModel,
                navigateToMainScreen = { navController.popBackStack() })
        }
    }

}