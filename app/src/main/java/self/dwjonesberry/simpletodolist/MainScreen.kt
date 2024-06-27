package self.dwjonesberry.simpletodolist

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import self.dwjonesberry.simpletodolist.ui.theme.SimpleToDoListTheme
import kotlin.coroutines.coroutineContext
import kotlin.math.exp

@Composable
fun MainScreen(navigateToAdd: () -> Unit) {
    val viewModel: TodoViewModel = viewModel()
    MainScreen(list = viewModel.getItems.invoke(), navigateToAdd = navigateToAdd)
}

@Composable
fun MainScreen(list: MutableList<TodoItem>, navigateToAdd: () -> Unit) {

    var filter by remember { mutableStateOf(0)}
    val setFilter: (Int) -> Unit = {
        filter = it
    }
    var filteredList = listOf<TodoItem>()
    if (filter > 0) {
        filteredList = list.filter { item ->
            item.priority.ordinal == filter
        }
    } else {
        filteredList = list
    }

    Column {
        MainActionBar(navigateToAdd = navigateToAdd, filter = setFilter)
        MainLazyList(list = filteredList)
    }
}

@Composable
fun MainActionBar(navigateToAdd: () -> Unit, filter: (Int) -> Unit) {
    Column() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navigateToAdd.invoke() }) {
            Text("Add Item")
        }
    }
        Row() {
            Button(onClick = {
                filter.invoke(3)
            }) {
                Text("H")
            }
            Button(onClick = {
                filter.invoke(2)
            }) {
                Text("M")
            }
            Button(onClick = {
                filter.invoke(1)
            }) {
                Text("L")
            }
            Button(onClick = {
                filter.invoke(0)
            }) {
                Text("N")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainLazyList(list: List<TodoItem>) {
    val context = LocalContext.current
    LazyColumn {
        items(
            count = list.size
        ) { ListItem(item = list[it], index = it) }
        }
}

fun changeBackground(checked: Boolean): Color {
    if (checked) return Color.LightGray
    else return Color.White
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItem(item: TodoItem, index: Int) {
    var background by remember { mutableStateOf(Color.White) }
    var expanded by remember { mutableStateOf(false) }
    background = changeBackground(item.checked)
    Box(
        modifier =
        Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .border(width = 1.dp, color = Color.Black)
            .fillMaxWidth()
            .background(background)
            .combinedClickable(onDoubleClick = {
                expanded = !expanded
                Log.d("MyProject", "item $index double clicked")
            }) {
                item.checked = !(item.checked)
                background = changeBackground(item.checked)
                Log.d("MyProject", "checked = ${item.checked}")
            }
    ) {
        Column() {
            Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),) {
                var str: String = (index + 1).toString()
                if ((index + 1) < 10) {
                    str = "0$str"
                }
                str = "$str:"

                Text(
                    modifier = Modifier.width(30.dp),
                    text = str
                )
                Text(
                    text = item.text
                )
            }
            if (expanded) {
                Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                    Button(
                        onClick = {
                            Log.d("MyProject", "edit button clicked")
                        }) {
                        Text("Edit")
                    }
                    Button(
                        onClick = {
                            Log.d("MyProject", "item #${index}: increase priority button pressed")
                            Log.d("MyProject", "current priority: ${item.priority.name}")
                            item.increasePriority()
                            Log.d("MyProject", "new priority: ${item.priority.name}")
                        }) {
                        Text("Increase Priority")
                    }
                    Button(
                        onClick = {
                            Log.d("MyProject", "item #${index}: decrease priority button pressed")
                            Log.d("MyProject", "current priority: ${item.priority.name}")
                            item.decreasePriority()
                            Log.d("MyProject", "new priority: ${item.priority.name}")
                        }
                    ) {
                        Text("Decrease Priority")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    val list = mutableListOf(TodoItem("Hello"), TodoItem("Goodbye"))
    SimpleToDoListTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen(list, {})
        }
    }
}