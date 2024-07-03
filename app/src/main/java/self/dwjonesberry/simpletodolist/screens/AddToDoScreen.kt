package self.dwjonesberry.simpletodolist.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import self.dwjonesberry.simpletodolist.FirebaseRepository
import self.dwjonesberry.simpletodolist.TodoViewModel
import self.dwjonesberry.simpletodolist.TodoViewModelFactory

@Composable
fun AddTodoScreen(repo: FirebaseRepository, viewModel: TodoViewModel = viewModel( factory = TodoViewModelFactory(repo)), navigateToMainScreen: () -> Unit) {
    AddTodoScreen(
        holdingText = viewModel.text,
        holdingNotes = viewModel.notes,
        addToList = viewModel.add,
        setText = viewModel.setText,
        setNotes = viewModel.setNotes,
        navigateToMainScreen = navigateToMainScreen
    )
}

@Composable
fun AddTodoScreen(
    holdingText: String,
    holdingNotes: String,
    addToList: () -> Unit,
    setText: (String) -> Unit,
    setNotes: (String) -> Unit,
    navigateToMainScreen: () -> Unit
) {
    Column {
        AddActionBar(addToList, navigateToMainScreen)
        AddTodoText(holdingText, setText)
        AddTodoNotes(holdingNotes, setNotes)
    }
}

@Composable
fun AddActionBar(addToList: () -> Unit, navigateToMainScreen: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            addToList.invoke()
            navigateToMainScreen.invoke()
        }) {
            Text("Add to List")
        }
//        Button(onClick = { /*TODO*/ }) {
//            Text("Goodbye")
//        }
    }
}

@Composable
fun AddTodoText(holdingText: String, setText: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    if (holdingText.isNotBlank()) {
        text = holdingText
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 5.dp),
        label = {Text("Title")},
        colors = TextFieldDefaults.colors().copy(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        minLines = 2,
        maxLines = 2,
        value = text,
        onValueChange = {
            setText.invoke(it)
            text = it
        }
    )
}

@Composable
fun AddTodoNotes(holdingNotes: String, setNotes: (String) -> Unit) {
    var notes by remember { mutableStateOf("") }
    if (holdingNotes.isNotBlank()) {
        notes = holdingNotes
    }
    OutlinedTextField(value = notes, modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 5.dp),
        label = {Text("Notes")},
        minLines = 8,
        maxLines = 8,
        colors = TextFieldDefaults.colors().copy(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
        ),
        onValueChange = {
        setNotes.invoke(it)
        notes = it
    })
}

@Preview
@Composable
fun ATSPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        AddTodoScreen("", "", {}, {}, {}, {})
    }
}