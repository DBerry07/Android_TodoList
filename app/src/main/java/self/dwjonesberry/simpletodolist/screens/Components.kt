package self.dwjonesberry.simpletodolist.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import self.dwjonesberry.simpletodolist.MyAppBar


@Composable
fun ActionBar(map: HashMap<String, List<() -> Unit>>) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        for (key in map.keys) {
            Button(onClick = {
                for (function in map.getValue(key)) {
                    function.invoke()
                }
            }) {
                Text(key)
            }
        }
    }
}

@Composable
fun MyAppBar() {

}