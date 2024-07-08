package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import self.dwjonesberry.simpletodolist.ui.theme.PurpleGrey40


@Composable
fun ActionBar(buttons: List<Pair<@Composable () -> Unit, List<() -> Unit>>>) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp), horizontalArrangement = Arrangement.SpaceAround) {
        for (pair in buttons) {
            Button(onClick = {
                for (function in pair.second) {
                    function.invoke()
                }
            }) {
                pair.first.invoke()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppBar(title: String, buttons: List<@Composable () -> Unit>) {
    val TAG = "TodoAppBar"
    TopAppBar(
        title = { Text(title) },
        navigationIcon = { buttons[0].invoke() },
        actions = {
            for (index in 1..<buttons.size) {
                buttons[index].invoke()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(
            //todo: change to better colour?
            containerColor = Color.LightGray
        )
    )
}

@Composable
fun AppBarButton(function: () -> Unit, icon: ImageVector, description: String) {
    IconButton(onClick = { function.invoke() }) {
        Icon(icon, description)
    }
}