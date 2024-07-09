package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import self.dwjonesberry.simpletodolist.data.DummyList
import self.dwjonesberry.simpletodolist.data.FirebaseRepository
import self.dwjonesberry.simpletodolist.data.Priority
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

    var filter by remember { mutableStateOf(Priority.NORMAL) }
    val setFilter: (Priority) -> Unit = {
        filter = it
    }

    Scaffold(
        floatingActionButton = { FloatingActionButton(onClick = { navigateToAddToDoScreen.invoke() }) {
            Icon(Icons.Default.Add, "Add a task")
        } },
        topBar = {
        MainAppBar(
            navigateToAddToDoScreen = navigateToAddToDoScreen,
            setSortedBy = viewModel.setSortedBy,
            setFilterBy = setFilter,
        )
    }) { padding ->
        MainLayout(
            modifier = Modifier.padding(padding),
            list = remembered,
            update = viewModel.update,
            deleteFromList = viewModel.delete,
            filter = filter
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
    filter: Priority,
) {
    val uncompletedTodos = list.filter { !it.checked }
    val completedTodos = list.filter { it.checked }


    var uncompletedTodosFiltered = listOf<TodoItem>()
    var completedTodosFiltered = listOf<TodoItem>()
    if (filter != Priority.NORMAL) {
        uncompletedTodosFiltered = uncompletedTodos.filter { item ->
            item.priority == filter
        }
        completedTodosFiltered = completedTodos.filter { item ->
            item.priority == filter
        }
    } else {
        uncompletedTodosFiltered = uncompletedTodos
        completedTodosFiltered = completedTodos
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
                    list = uncompletedTodosFiltered,
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
                    list = completedTodosFiltered,
                    update = update,
                    deleteFromList = deleteFromList,
                )
            }
        }
    }
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

@Preview
@Composable
fun MainPreview() {
    val list = mutableListOf(TodoItem(0, "Hello"), TodoItem(1, "Goodbye"))
    SimpleToDoListTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MainLayout(list = DummyList,
                deleteFromList = {}, update = {}, modifier = Modifier, filter = Priority.NORMAL
            )
        }
    }
}
