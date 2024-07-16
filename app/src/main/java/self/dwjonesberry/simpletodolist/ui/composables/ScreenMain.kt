package self.dwjonesberry.simpletodolist.ui.composables

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
import androidx.compose.ui.graphics.Color
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
import self.dwjonesberry.simpletodolist.data.MyTask
import self.dwjonesberry.simpletodolist.data.Sort
import self.dwjonesberry.simpletodolist.data.TaskList
import self.dwjonesberry.simpletodolist.data.TaskListRepo
import self.dwjonesberry.simpletodolist.data.TaskViewModel
import self.dwjonesberry.simpletodolist.data.TaskViewModelFactory
import self.dwjonesberry.simpletodolist.ui.theme.SimpleToDoListTheme
import java.util.Locale

private val TAG: String = "MyProject:MainScreen"

/**
 * A [Composable] function that defines the entire first and main screen of the app. Primary [Composable]
 * is a [Scaffold] that holds a top app bar of [MainAppBar], the [ListLayout], and a [FloatingActionButton].
 * @param repo a [FirebaseRepository], which is a class defined in this app.
 * @param viewModel a view model for the screen; defaults to a [TaskViewModel].
 */
@Composable
fun MainLayout(
    repo: FirebaseRepository = FirebaseRepository(),
    viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(repo)),
) {
    val data by viewModel.todoList.collectAsState()
    val remembered = remember(data) { data }

    var filter by remember { mutableStateOf(viewModel.filter) }
    var sorted by remember { mutableStateOf(viewModel.sortedBy) }

    val setFilter: (Priority) -> Unit = {
        viewModel.filter = it
        filter = viewModel.filter
    }
    val setSorted: (Sort) -> Unit = {
        viewModel.sortedBy = it.ordinal
        sorted = viewModel.sortedBy
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.navigateToAddScreenWithArguments?.invoke(null) },
                containerColor = Color.White
            ) {
                Icon(Icons.Default.Add, "Add a task")
            }
        },
        topBar = {
            MainAppBar(
                setSortedBy = setSorted,
                setFilterBy = setFilter,
            )
        }) { padding ->
        ListLayout(
            modifier = Modifier.padding(padding),
            viewModel = viewModel,
            list = remembered,
            filter = filter,
        )
    }
}

/**
 * A [Composable] function that holds [SectionList] for each type of list (currently just "Completed" and
 * "Uncompleted"). Primary [Composable] is a [LazyColumn] inside a [Column].
 * - Compatible with [Preview]
 * @param modifier The passed [Modifier] for this composable.
 * @param list The actual [List] of [MyTask] that the whole app depends upon.
 * @param filter The user-selected [Priority]-based filter; based on which filter is selected,
 * only those [MyTask] with the same [Priority] as the filter will be shown.
 */
@Composable
private fun ListLayout(
    modifier: Modifier = Modifier,
    list: List<MyTask>,
    viewModel: TaskViewModel,
    filter: Priority,
) {


    if (filter != Priority.NORMAL) {
        TaskListRepo.sort(list.filter { item ->
            item.priority == filter
        })
    } else {
        TaskListRepo.sort(list)
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
                for (each in TaskListRepo.taskMap.keys) {
                    SectionList(
                        modifier = Modifier.height(height),
                        list = TaskListRepo.taskMap.get(each),
                        viewModel = viewModel,
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )
                }

            }
        }
    }
}

/**
 * A [Composable] that contains the [SectionHeading] and associated collection of [ListItem] (which
 * are [MyTask]). If the [SectionHeading] is clicked by the user, all the associated [ListItem]
 * are hidden in the UI from the user.
 * - Compatible with [Preview]
 * @param modifier The [Modifier] for this composable.
 * @param list The [List] that contains all the relevant [MyTask] associated with that heading.
 */
@Composable
private fun SectionList(
    modifier: Modifier = Modifier,
    list: TaskList?,
    viewModel: TaskViewModel,
) {
    val context = LocalContext.current
    var showSection: Boolean by remember { mutableStateOf(true) }

    Row(modifier = modifier
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
        if (list != null) {
            SectionHeading(list.heading)
        } else {
            SectionHeading(heading = "Unknown")
        }
    }
    if (showSection) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (list != null) {
                for (index in 0..<list.taskList.size) {
                    ListItem(
                        item = list.taskList.get(index),
                        index = index,
                        viewModel = viewModel,
                    )
                }
            }

        }
    } else {
//        Spacer(modifier = Modifier.padding(vertical = 20.dp))
    }
}

/**
 * A [Composable] that is used to display the title of the relevant [SectionList].
 * @param heading A [String] used for naming the section.
 */
@Composable
fun SectionHeading(heading: String) {
    Text(
        text = heading.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.CANADA) else it.toString() },
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
    )
}

@Preview
@Composable
fun MainPreview() {
    val vm = TaskViewModel(FirebaseRepository())
    val filter = Priority.NORMAL
    SimpleToDoListTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ListLayout(list = DummyList, viewModel = vm, filter = filter, modifier = Modifier)
        }
    }
}
