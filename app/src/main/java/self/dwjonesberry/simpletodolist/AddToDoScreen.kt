package self.dwjonesberry.simpletodolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddTodoScreen(navigateToMainScreen: () -> Unit) {
    val viewModel: TodoViewModel = viewModel()
    AddTodoScreen(
        holdingText = viewModel.text,
        addToList = viewModel.addToList,
        setText = viewModel.setText,
        navigateToMainScreen = navigateToMainScreen
    )
}

@Composable
fun AddTodoScreen(
    holdingText: String,
    addToList: () -> Unit,
    setText: (String) -> Unit,
    navigateToMainScreen: () -> Unit
) {
    Column {
        AddActionBar(addToList, navigateToMainScreen)
        AddTodoText(holdingText, setText)
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

    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text,
        onValueChange = {
            setText.invoke(it)
            text = it
        }
    )
}

@Preview
@Composable
fun ATSPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        AddTodoScreen({})
    }
}