package self.dwjonesberry.simpletodolist.ui.composables.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import self.dwjonesberry.simpletodolist.data.TaskViewModel

private val TAG: String = "AddTodoNotes"

@Composable
fun AddTodoNotes(viewModel: TaskViewModel) {
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