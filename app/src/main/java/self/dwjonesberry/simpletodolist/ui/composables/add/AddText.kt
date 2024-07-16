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

private val TAG: String = "AddTodoText"

@Composable
fun AddTodoText(viewModel: TaskViewModel) {
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