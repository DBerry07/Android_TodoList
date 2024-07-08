package self.dwjonesberry.simpletodolist.ui.composables

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import self.dwjonesberry.simpletodolist.data.DummyList
import self.dwjonesberry.simpletodolist.data.FirebaseRepository
import self.dwjonesberry.simpletodolist.data.Priority
import self.dwjonesberry.simpletodolist.data.Sort
import self.dwjonesberry.simpletodolist.data.TodoItem
import self.dwjonesberry.simpletodolist.data.TodoViewModel
import self.dwjonesberry.simpletodolist.data.TodoViewModelFactory
import self.dwjonesberry.simpletodolist.ui.theme.SimpleToDoListTheme

private val TAG: String = "MyProject:MainScreen"

@Composable
fun MainLayout(
    repo: FirebaseRepository = FirebaseRepository(),
    viewModel: TodoViewModel = viewModel(factory = TodoViewModelFactory(repo)),
    navigateToAddToDoScreen: () -> Unit,
) {
    val data by viewModel.todoList.collectAsState()
    val remembered = remember(data) { data }

    Scaffold(topBar = {
        MainAppBar(
            navigateToAddToDoScreen = navigateToAddToDoScreen,
            setSortedBy = viewModel.setSortedBy
        )
    }) { padding ->
        MainLayout(
            modifier = Modifier.padding(padding),
            list = remembered,
            update = viewModel.update,
            deleteFromList = viewModel.delete,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainLayout(
    modifier: Modifier,
    list: List<TodoItem>,
    update: (TodoItem) -> Unit,
    deleteFromList: (TodoItem) -> Unit,
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

    val height = LocalConfiguration.current.screenHeightDp.dp

    Column(modifier = modifier.height(height)) {
//        MainActionBar(filter = setFilter, sort = sort)
        LazyColumn(
            modifier = Modifier
                .height(height)
        ) {
            item {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                SectionList(
                    modifier = Modifier.height(height),
                    heading = "Uncompleted",
                    list = unFiltered,
                    update = update,
                    deleteFromList = deleteFromList,
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
                SectionList(
                    modifier = Modifier.height(height),
                    heading = "Completed",
                    list = comFiltered,
                    update = update,
                    deleteFromList = deleteFromList,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainAppBar(navigateToAddToDoScreen: () -> Unit, setSortedBy: (Sort) -> Unit) {
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

@Composable
private fun SectionList(
    modifier: Modifier,
    heading: String,
    list: List<TodoItem>,
    update: (TodoItem) -> Unit,
    deleteFromList: (TodoItem) -> Unit,
) {
    val context = LocalContext.current
    var showSection: Boolean by remember { mutableStateOf(true) }

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { showSection = !showSection }
        .padding(10.dp)) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .width(50.dp)
                .height(30.dp)
                .padding(horizontal = 5.dp)
        ) {
            if (!showSection) {
                Icon(
                    Icons.Default.ArrowDropDown,
                    "Show section",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        SectionHeading(heading)
    }
    if (showSection) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            for (index in 0..<list.size) {
                ListItem(
                    item = list[index],
                    index = index,
                    update = update,
                    deleteFromList = deleteFromList,
                )
            }

        }
    } else {
//        Spacer(modifier = Modifier.padding(vertical = 20.dp))
    }
}

@Composable
fun SectionHeading(heading: String) {
    Text(
        text = heading,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
    )
}

@Composable
fun AppBarDropDown(
    toggleDropDown: () -> Unit,
    getDropDown: () -> Boolean,
    setSortedBy: (Sort) -> Unit
) {
    DropdownMenu(
        expanded = getDropDown.invoke(),
        onDismissRequest = { toggleDropDown.invoke() }
    ) {
        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Default.KeyboardArrowUp, "Sort by ID ascending") },
            text = {
                Text("ID", fontFamily = FontFamily.Monospace)
            },
            onClick = {
                setSortedBy.invoke(Sort.ID_ASC)
                toggleDropDown.invoke()
            })
        DropdownMenuItem(leadingIcon = {
            Icon(
                Icons.Default.KeyboardArrowDown,
                "Sort by ID descending"
            )
        }, text = {
            Text("ID", fontFamily = FontFamily.Monospace)
        }, onClick = {
            setSortedBy.invoke(Sort.ID_DEC)
            toggleDropDown.invoke()
        })
        DropdownMenuItem(leadingIcon = {
            Icon(
                Icons.Default.KeyboardArrowUp,
                "Sort by Priority ascending"
            )
        }, text = {
            Text("Priority", fontFamily = FontFamily.Monospace)
        }, onClick = {
            setSortedBy(Sort.PR_ASC)
            toggleDropDown.invoke()
        })
        DropdownMenuItem(leadingIcon = {
            Icon(
                Icons.Default.KeyboardArrowDown,
                "Sort by Priority descending"
            )
        }, text = {
            Text("Priority", fontFamily = FontFamily.Monospace)
        }, onClick = {
            setSortedBy.invoke(Sort.PR_DEC)
            toggleDropDown.invoke()
        })
    }
}

@Preview
@Composable
fun MainPreview() {
    val list = mutableListOf(TodoItem(0, "Hello"), TodoItem(1, "Goodbye"))
    SimpleToDoListTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainLayout(list = DummyList,
                deleteFromList = {}, update = {}, modifier = Modifier
            )
        }
    }
}
