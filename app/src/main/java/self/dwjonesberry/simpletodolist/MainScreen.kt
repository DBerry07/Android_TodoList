package self.dwjonesberry.simpletodolist

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import self.dwjonesberry.simpletodolist.ui.theme.SimpleToDoListTheme

@Composable
fun MainScreen(navigateToAdd: () -> Unit) {
    val viewModel: TodoViewModel = viewModel()
    MainScreen(list = viewModel.todoItems)
}

@Composable
fun MainScreen(list: MutableList<String>) {
    Column {
        MainActionBar()
        MainLazyList(list = list)
    }
}

@Composable
fun MainActionBar() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
      Button(onClick = { /*TODO*/ }) {
          Text("Button 1")
      }
        Button(onClick = { /*TODO*/ }) {
            Text("Button 2")
        }
    }
}

@Composable
fun MainLazyList(list: MutableList<String>) {
    LazyColumn {
        items(
            count = list.size
        ) {
            Box(
                modifier =
                Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .border(width = 1.dp, color = Color.Black)
                    .fillMaxWidth()) {
              Text(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                  text = list[it])
            }
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    val list = mutableListOf("Hello", "Goodbye")
    SimpleToDoListTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
           MainScreen(list)
        }
    }
}