package self.dwjonesberry.simpletodolist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

class MyAppBar {


    val Composable: Unit
        @Composable
        get() {return TodoAppBar()}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TodoAppBar() {
            TopAppBar(
                title = { Text("Task List") },
                navigationIcon = { Icon(Icons.Default.Menu, "Menu button") },
                actions = {
                    Button(onClick = {}) {Text("Add Item")}
                }
        )
    }
}