package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import self.dwjonesberry.simpletodolist.data.FirebaseRepository
import self.dwjonesberry.simpletodolist.data.TaskViewModel
import self.dwjonesberry.simpletodolist.data.TaskViewModelFactory


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

@Composable
private fun AddActionBar(
    viewModel: TaskViewModel,
    isEditing: Boolean
) {

    var buttons: List<Pair<@Composable () -> Unit, List<(() -> Unit)?>>>? = null

    if (!isEditing) {
        val row: @Composable () -> Unit = {
            Row() {
                Text("Add")
            }
        }

        buttons = listOf(
            Pair(row, listOf(viewModel.add, viewModel.popBackStack)),
        )
    } else {
        val row: @Composable () -> Unit = {
            Row() {
                Text("Update")
            }
        }
        buttons = listOf(
            Pair(row, listOf(viewModel.updateSelected, viewModel.navigateToMainScreen)),
        )
    }
    ActionBar(buttons = buttons)
}

@Composable
private fun AddTodoText(viewModel: TaskViewModel) {
    var text by remember { mutableStateOf(viewModel.title) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp),
        label = { Text("Title") },
        colors = TextFieldDefaults.colors().copy(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        minLines = 2,
        maxLines = 2,
        value = text,
        onValueChange = {
            viewModel.setText(it)
            text = it
        }
    )
}

@Composable
private fun AddTodoNotes(viewModel: TaskViewModel) {
    var notes by remember { mutableStateOf(viewModel.notes) }

    OutlinedTextField(value = notes,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp),
        label = { Text("Notes") },
        minLines = 8,
        maxLines = 8,
        colors = TextFieldDefaults.colors().copy(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        onValueChange = {
            viewModel.setNotes(it)
            notes = it
        })
}

@Preview
@Composable
fun ATSPreview() {
    val vm = TaskViewModel(FirebaseRepository())

    Surface(modifier = Modifier.fillMaxSize()) {
        AddTaskLayout(vm, false)
    }
}