package self.dwjonesberry.simpletodolist

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MainScreen() {
    val viewModel: TodoViewModel = viewModel()
}