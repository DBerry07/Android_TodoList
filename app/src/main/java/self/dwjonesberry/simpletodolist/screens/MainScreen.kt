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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import self.dwjonesberry.simpletodolist.FirebaseRepository
import self.dwjonesberry.simpletodolist.Priority
import self.dwjonesberry.simpletodolist.Screens
import self.dwjonesberry.simpletodolist.TodoItem
import self.dwjonesberry.simpletodolist.TodoViewModel
import self.dwjonesberry.simpletodolist.TodoViewModelFactory
import self.dwjonesberry.simpletodolist.ui.theme.SimpleToDoListTheme

class MainScreen(val navigate: () -> Unit) {

    val TAG = "MyProject:MainScreen"

    val Screen: Unit
        @Composable
        get() {
            return MainLayout(repo = FirebaseRepository(), navigateToAdd = navigate)
        }

    @Composable
    private fun MainLayout(
        repo: FirebaseRepository,
        viewModel: TodoViewModel = viewModel(factory = TodoViewModelFactory(repo)),
        navigateToAdd: () -> Unit
    ) {
        val data by viewModel.todoList.collectAsState()
        val remembered = remember(data) { data }

        MainLayout(
            list = remembered,
            navigateToAdd = navigateToAdd,
            update = viewModel.update,
            deleteFromList = viewModel.delete,
            sort = viewModel.cycleSort,
            refresh = viewModel.refresh,
            sortedBy = viewModel.sortedBy,
        )
    }

    @Composable
    private fun MainLayout(
        list: List<TodoItem>,
        navigateToAdd: () -> Unit,
        update: (TodoItem) -> Unit,
        deleteFromList: (TodoItem) -> Unit,
        sort: () -> Unit,
        refresh: () -> Unit,
        sortedBy: Int,
    ) {
        val un = list.filter { !it.checked }
        val com = list.filter { it.checked }

        var filter by remember { mutableStateOf(0) }
        val setFilter: (Int) -> Unit = {
            filter = it
        }
        var unFiltered = listOf<TodoItem>()
        var comFiltered = listOf<TodoItem>()
        if (filter > 0) {
            unFiltered = un.filter { item ->
                item.priority.ordinal == filter
            }
            comFiltered = com.filter { item ->
                item.priority.ordinal == filter
            }
        } else {
            unFiltered = un
            comFiltered = com
        }

        Column {
            MainActionBar(navigateToAdd = navigateToAdd, filter = setFilter, sort = sort, sortedBy = sortedBy)
            MyLazyList(list = unFiltered, update = update, deleteFromList = deleteFromList, refresh = refresh)
            MyLazyList(list = comFiltered, update = update, deleteFromList = deleteFromList, refresh = refresh)
        }
    }

    @Composable
    private fun MainActionBar(navigateToAdd: () -> Unit, filter: (Int) -> Unit, sort: () -> Unit, sortedBy: Int) {
        var sortedByText = "Sorted:"

        when(sortedBy) {
            0 -> sortedByText = "${sortedByText} IDup"
            1 -> sortedByText = "${sortedByText} IDdn"
            2 -> sortedByText = "${sortedByText} PRup"
            3 -> sortedByText = "${sortedByText} PRdn"
        }

        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = { navigateToAdd.invoke() }) {
                    Text("Add Item")
                }
                Button(onClick = { sort.invoke() }) {
                    Text(sortedByText)
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
    private fun MyLazyList(
        list: List<TodoItem>,
        update: (TodoItem) -> Unit,
        deleteFromList: (TodoItem) -> Unit,
        refresh: () -> Unit,
    ) {
        val context = LocalContext.current
        Text("Heading")
        LazyColumn {
            items(
                count = list.size
            ) {
                ListItem(
                    item = list[it],
                    index = it,
                    update = update,
                    deleteFromList = deleteFromList,
                    refresh = refresh,
                )
            }
        }
    }

    private fun changeBackground(checked: Boolean): Color {
        if (checked) return Color.LightGray
        else return Color.White
    }

    fun determineBorder(todoItem: TodoItem): Color {
        return when (todoItem.priority) {
            Priority.NORMAL -> {
                Color.Black
            }

            Priority.LOW -> {
                Color.Green
            }

            Priority.MEDIUM -> {
                Color.Blue
            }

            Priority.HIGH -> {
                Color.Red
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ListItem(
        item: TodoItem,
        index: Int,
        update: (TodoItem) -> Unit,
        deleteFromList: (TodoItem) -> Unit,
        refresh: () -> Unit
    ) {
        var background by remember { mutableStateOf(Color.White) }
        var expanded by remember { mutableStateOf(false) }
        var border: Color by remember { mutableStateOf(Color.Black) }
        background = changeBackground(item.checked)
        border = determineBorder(item)
        Card(
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors().copy(
                containerColor = background
            ),
            modifier =
            Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .border(2.dp, border, shape = MaterialTheme.shapes.medium)
                .fillMaxWidth()
                .combinedClickable(onDoubleClick = {
                    item.checked = !(item.checked)
                    background = changeBackground(item.checked)
                    Log.d("MyProject", "checked = ${item.checked}")
                    update(item)
//                    refresh.invoke()
                }) {
                    expanded = !expanded
                    Log.d("MyProject", "item $index double clicked")
                }
        ) {
            Column() {
                Row(
                    modifier =
                    Modifier
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                )
                {
                    var str: String = item.id.toString()
                    if ((item.id) < 10) {
                        str = "0$str"
                    }
                    if ((item.id) < 100) {
                        str = "0$str"
                    }
                    str = "$str:"

                    Text(
                        modifier = Modifier.width(60.dp),
                        text = str,
                        color = border,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displaySmall,
                        fontFamily = FontFamily.Monospace,
                    )
                    Text(
                        text = item.text,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.displayMedium,
                        maxLines =
                            if (!expanded) 1
                            else 10,
                        overflow = TextOverflow.Ellipsis
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
                                    Log.d("MyProject", "edit button clicked on item #${item.id}")
                                }) {
                                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit item number ${item.id}")
                            }
                            Button(
                                onClick = {
                                    Log.d(
                                        "MyProject",
                                        "item #${item.id}: increase priority button pressed"
                                    )
                                    Log.d("MyProject", "current priority: ${item.priority.name}")
                                    item.increasePriority()
                                    update(item)
                                    Log.d("MyProject", "new priority: ${item.priority.name}")
                                }) {
                                Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = "Increase priority of item number ${item.id}")
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
                                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "Decrease priority of item number ${item.id}")
                            }
                            Button(onClick = {
                                Log.d(TAG, "delete button pressed on item #${item.id}")
                                deleteFromList.invoke(item)
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete item ${item.id}.")
                            }
                        }
                    }
                }
            }
        }
    }
}