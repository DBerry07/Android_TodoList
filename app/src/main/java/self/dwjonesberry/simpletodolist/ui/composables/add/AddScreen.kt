package self.dwjonesberry.simpletodolist.ui.composables.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import self.dwjonesberry.simpletodolist.data.FirebaseRepository
import self.dwjonesberry.simpletodolist.data.TaskViewModel
import self.dwjonesberry.simpletodolist.data.TaskViewModelFactory
import self.dwjonesberry.simpletodolist.ui.composables.ActionBar

private val TAG: String = "AddScreen"

@Composable
fun AddTaskScreen(
    repo: FirebaseRepository = FirebaseRepository(),
    viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(repo)),
) {
    AddTaskLayout(
        viewModel = viewModel,
        isEditing = if (viewModel.selectedTodo != null) true else false
    )
}

@Composable
private fun AddTaskLayout(
    viewModel: TaskViewModel,
    isEditing: Boolean,
) {
    Column {
        AddActionBar(viewModel = viewModel, isEditing = isEditing)
        AddTodoText(viewModel = viewModel)
        AddTodoNotes(viewModel = viewModel)
    }
}

@Preview
@Composable
fun ATSPreview() {
    val vm = TaskViewModel(FirebaseRepository())

    Surface(modifier = Modifier.fillMaxSize()) {
        AddTaskLayout(vm, false)
    }
}