package self.dwjonesberry.simpletodolist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

class MyAppBar(val navigateToAddScreen: () -> Unit, val navigateToMainScreen: () -> Unit) {

    val Composable: Unit
        @Composable
        get() {return TodoAppBar(navigateToAddScreen = navigateToAddScreen, navigateToMainScreen = navigateToMainScreen)}

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TodoAppBar(navigateToAddScreen: () -> Unit, navigateToMainScreen: () -> Unit) {
            TopAppBar(
                title = { Text("Task List") },
                navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, "Menu button") } },
                actions = {
                    IconButton(onClick = {navigateToAddScreen.invoke()}) {Icon(imageVector = Icons.Default.Add, "Add todo item")}
                }
        )
    }
}