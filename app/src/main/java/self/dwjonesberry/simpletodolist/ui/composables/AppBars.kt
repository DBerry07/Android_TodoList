package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import self.dwjonesberry.simpletodolist.data.Sort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(navigateToAddToDoScreen: () -> Unit, setSortedBy: (Sort) -> Unit) {
    var dropDown by remember { mutableStateOf(false) }

    val getDropDown: () -> Boolean = {
        dropDown
    }
    val toggleDropDown: () -> Unit = {
        dropDown = !dropDown
    }
    TopAppBar(title = { Text("Task List") }, actions = {
        Row() {
            IconButton(onClick = { navigateToAddToDoScreen.invoke() }) {
                Icon(Icons.Default.Add, "Add a task")
            }
            IconButton(onClick = { dropDown = !dropDown }) {
                Icon(Icons.Default.Menu, "Sort menu")
            }
            if (dropDown) {
                AppBarDropDown(toggleDropDown, getDropDown, setSortedBy)
            }
        }
    })
}