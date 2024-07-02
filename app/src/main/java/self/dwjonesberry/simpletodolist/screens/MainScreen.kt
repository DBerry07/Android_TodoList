package self.dwjonesberry.simpletodolist.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import self.dwjonesberry.simpletodolist.TodoItem
import self.dwjonesberry.simpletodolist.TodoViewModel
import self.dwjonesberry.simpletodolist.ui.theme.SimpleToDoListTheme

// TODO: The list from viewModel still requires the user to reload the screen. Need to fix!
@Composable
fun MainScreen(navigateToAdd: () -> Unit) {
    val viewModel: TodoViewModel = viewModel()
    val data by viewModel.getAll.invoke().collectAsState()
    MainScreen(
        list = data,
        navigateToAdd = navigateToAdd,
        update = viewModel.update,
        deleteFromList = viewModel.delete
    )
}

@Composable
fun MainScreen(list: MutableList<TodoItem>, navigateToAdd: () -> Unit, update: (TodoItem) -> Unit, deleteFromList: (TodoItem) -> Unit) {

    var filter by remember { mutableStateOf(0) }
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
        MainLazyList(list = filteredList, update = update, deleteFromList = deleteFromList)
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = {
                filter.invoke(0)
            }) {
                Text("N")
            }
            Button(onClick = {
                filter.invoke(1)
            }) {
                Text("L")
            }
            Button(onClick = {
                filter.invoke(2)
            }) {
                Text("M")
            }
            Button(onClick = {
                filter.invoke(3)
            }) {
                Text("H")
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainLazyList(list: List<TodoItem>, update: (TodoItem) -> Unit, deleteFromList: (TodoItem) -> Unit) {
    val context = LocalContext.current
    LazyColumn {
        items(
            count = list.size
        ) { ListItem(item = list[it], index = it, update = update, deleteFromList = deleteFromList) }
    }
}

fun changeBackground(checked: Boolean): Color {
    if (checked) return Color.LightGray
    else return Color.White
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItem(item: TodoItem, index: Int, update: (TodoItem) -> Unit, deleteFromList: (TodoItem) -> Unit) {
    var background by remember { mutableStateOf(Color.White) }
    var expanded by remember { mutableStateOf(false) }
    background = changeBackground(item.checked)
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors().copy(
            containerColor = background
        ),
        modifier =
        Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .border(1.dp, Color.Black, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .combinedClickable(onDoubleClick = {
                item.checked = !(item.checked)
                background = changeBackground(item.checked)
                Log.d("MyProject", "checked = ${item.checked}")
            }) {
                expanded = !expanded
                Log.d("MyProject", "item $index double clicked")
            }
    ) {
        Column() {
            Row(modifier =
            Modifier
                .padding(horizontal = 10.dp, vertical = 10.dp))
            {
                var str: String = (index + 1).toString()
                if ((index + 1) < 10) {
                    str = "0$str"
                }
                str = "$str:"

                Text(
                    modifier = Modifier.width(40.dp),
                    text = str,
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = item.text,
                    style = MaterialTheme.typography.displayMedium
                )
            }
            if (expanded) {
                Column {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                        Text(item.notes)
                    }
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                        Button(
                            onClick = {
                                Log.d("MyProject", "edit button clicked")
                            }) {
                            Text("Edit")
                        }
                        Button(
                            onClick = {
                                Log.d(
                                    "MyProject",
                                    "item #${index}: increase priority button pressed"
                                )
                                Log.d("MyProject", "current priority: ${item.priority.name}")
                                item.increasePriority()
                                update(item)
                                Log.d("MyProject", "new priority: ${item.priority.name}")
                            }) {
                            Text("UP")
                        }
                        Button(
                            onClick = {
                                Log.d(
                                    "MyProject",
                                    "item #${index}: decrease priority button pressed"
                                )
                                Log.d("MyProject", "current priority: ${item.priority.name}")
                                item.decreasePriority()
                                update(item)
                                Log.d("MyProject", "new priority: ${item.priority.name}")
                            }
                        ) {
                            Text("DWN")
                        }
                        Button(onClick = {
                            deleteFromList.invoke(item)
                        }) {
                            Text("DEL")
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    val list = mutableListOf(TodoItem(0, "Hello"), TodoItem(1, "Goodbye"))
    SimpleToDoListTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen(list, {}, {}, {})
        }
    }
}