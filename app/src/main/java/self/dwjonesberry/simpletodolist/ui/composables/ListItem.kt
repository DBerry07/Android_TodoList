package self.dwjonesberry.simpletodolist.ui.composables

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import self.dwjonesberry.simpletodolist.data.DummyTodo
import self.dwjonesberry.simpletodolist.data.FirebaseRepository
import self.dwjonesberry.simpletodolist.data.MyTask
import self.dwjonesberry.simpletodolist.data.TaskViewModel

/**
 * The [Composable] used to display each entry in the [List] of [MyTask] retrieved from the database.
 * Used in conjunction with [MainLayout].
 * @param item The [MyTask] that this composable will show in the UI.
 * @param index The index of the [item] in the list of [MyTask]
 * @see [MainLayout]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItem(
    viewModel: TaskViewModel,
    item: MyTask,
    index: Int,

    ) {
    var bgColour by remember { mutableStateOf(Color.White) }
    var isDialogShowing by remember { mutableStateOf(false) }
    var borderColour: Color by remember { mutableStateOf(Color.Black) }
    bgColour = changeBackground(item.checked)
    borderColour = determineBorder(item)
    ElevatedCard(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors().copy(
            containerColor = bgColour
        ),
        modifier =
        Modifier
            .height(55.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .border(2.dp, borderColour, shape = MaterialTheme.shapes.medium)
            .fillMaxWidth()
            .combinedClickable(onDoubleClick = {
                item.checked = !(item.checked)
                bgColour = changeBackground(item.checked)
                Log.d("MyProject", "checked = ${item.checked}")
                viewModel.update(item)
//                    refresh.invoke()
            }) {
                isDialogShowing = !isDialogShowing
                Log.d("MyProject", "item $index double clicked")
            }
    ) {
        ListItemText(item, borderColour)
        if (isDialogShowing) {
            ListItemPopUp(
                modifier = Modifier,
                viewModel = viewModel,
                onDismissRequest = { isDialogShowing = !isDialogShowing },
                myTask = item,
            )
        }
    }
}

@Composable
fun ListItemText(item: MyTask, borderColour: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        var str: String = item.id.toString()
        if ((item.id) < 10) {
            str = "0$str"
        }
        if ((item.id) < 100) {
            str = "0$str"
        }
        str = "$str:"
        Column(
            modifier = Modifier
                .background(borderColour)
                .fillMaxHeight()
                .padding(5.dp, 0.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .width(70.dp)
                    .padding(horizontal = 10.dp),
                text = str,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displaySmall,
                fontFamily = FontFamily.Monospace,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
            Modifier
                .padding(10.dp, 0.dp, 10.dp, 0.dp)
        )
        {
            Text(
                text = item.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun ListItemPreview() {
    ListItem(viewModel = TaskViewModel(FirebaseRepository()), index = 0, item = DummyTodo)
}