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
import self.dwjonesberry.simpletodolist.data.TaskViewModel
import self.dwjonesberry.simpletodolist.data.TaskViewModelFactory
import self.dwjonesberry.simpletodolist.ui.theme.SimpleToDoListTheme

private val TAG: String = "MyProject:MainScreen"

/**
 * A [Composable] function that defines the entire first and main screen of the app. Primary [Composable]
 * is a [Scaffold] that holds a top app bar of [MainAppBar], the [ListLayout], and a [FloatingActionButton].
 * - Not compatible with [Preview] because of inclusion of a view model.
 * @param repo a [FirebaseRepository], which is a class defined in this app.
 * @param viewModel a view model for the screen; defaults to a [TaskViewModel].
 * @param navigateToAddToDoScreen the function (passed from [MyApp]) that navigates the user
 * from this [MainLayout] to [AddToDoScreen].
 */
@Composable
fun MainLayout(
    repo: FirebaseRepository = FirebaseRepository(),
    viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(repo)),
) {
    val data by viewModel.todoList.collectAsState()
    val remembered = remember(data) { data }

    var filter by remember { mutableStateOf(Priority.NORMAL) }
    val setFilter: (Priority) -> Unit = {
        filter = it
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
                setSortedBy = viewModel.setSortedBy,
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
 * @param update A lambda function that updates the database (and thus also the UI) with new information
 * regarding a particular [MyTask].
 * @param deleteFromList A lambda function that deletes a [MyTask] from the database. The UI is
 * automatically updated when this happens.
 * @param filter The user-selected [Priority]-based filter; based on which filter is selected,
 * only those [MyTask] with the same [Priority] as the filter will be shown.
 */
@Composable
private fun ListLayout(
    modifier: Modifier,
    list: List<MyTask>,
    viewModel: TaskViewModel,
    filter: Priority,
) {
    val uncompletedTodos = list.filter { !it.checked }
    val completedTodos = list.filter { it.checked }


    var uncompletedTodosFiltered = listOf<MyTask>()
    var completedTodosFiltered = listOf<MyTask>()
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
                    viewModel = viewModel,
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
                    viewModel = viewModel,
                )
            }
        }
    }
}

/**
 * A [Composable] that contains the [SectionHeading] and associated collection of [ListItem] (which
 * are [MyTask]). If the [SectionHeading] is clicked by the user, all the associated [ListItem]
 * are hidden in the UI from the user.
 * - Compatible with [Preview]
 * @param modifier The [Modifier] for this composable. Currently unused.
 * @param heading The [String] used to name the section. Passed to [SectionHeading].
 * @param list The [List] that contains all the relevant [MyTask] associated with that heading.
 * @param update The lambda function that updates the database with new information of one [MyTask].
 * The UI automatically updates to reflect any changes.
 * @param deleteFromList The lambda function that deletes a [MyTask] from the database. The UI automatically
 * updates to reflect the change.
 */
@Composable
private fun SectionList(
    modifier: Modifier,
    heading: String,
    list: List<MyTask>,
    viewModel: TaskViewModel,
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
                    viewModel = viewModel,
                )
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
        text = heading,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
    )
}

@Preview
@Composable
fun MainPreview() {
    val list = mutableListOf(MyTask(0, "Hello"), MyTask(1, "Goodbye"))
    val vm = TaskViewModel(FirebaseRepository())
    val filter = Priority.NORMAL
    SimpleToDoListTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ListLayout(list = DummyList, viewModel = vm, filter = filter, modifier = Modifier)
        }
    }
}
