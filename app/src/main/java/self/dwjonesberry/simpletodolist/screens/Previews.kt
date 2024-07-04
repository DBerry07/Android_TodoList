package self.dwjonesberry.simpletodolist.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import self.dwjonesberry.simpletodolist.TodoItem
import self.dwjonesberry.simpletodolist.ui.theme.SimpleToDoListTheme

@Preview
@Composable
fun MainPreview() {
    val list = mutableListOf(TodoItem(0, "Hello"), TodoItem(1, "Goodbye"))
    SimpleToDoListTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen().Screen
        }
    }
}

@Preview
@Composable
fun ATSPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        AddToDoScreen({}).Screen
    }
}