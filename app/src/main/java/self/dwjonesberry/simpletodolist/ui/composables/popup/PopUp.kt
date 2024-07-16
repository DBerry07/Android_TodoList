package self.dwjonesberry.simpletodolist.ui.composables.popup

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import self.dwjonesberry.simpletodolist.data.DummyTodo
import self.dwjonesberry.simpletodolist.data.FirebaseRepository
import self.dwjonesberry.simpletodolist.data.Priority
import self.dwjonesberry.simpletodolist.data.MyTask
import self.dwjonesberry.simpletodolist.data.TaskViewModel


private val TAG: String = "MyProject:PopUp"

/**
 * A [Composable] for the dialog pop-up which displays the [MyTask] details. Shows when a user
 * clicks on a [ListItem] and hides when dismissed. The composable itself acts as a container for
 * the other parts of the dialog, namely the [PopUpTextDisplay] and the [PopUpActonBar].
 * @param modifier The modifier for this composable.
 * @param onDismissRequest The lambda function that contains the actions taken by the app when the
 * user dismisses the dialog. Currently, only hides the dialog on dismiss.
 * @param myTask The [MyTask] associated with the [ListItem] that this [ListItemPopUp] corresponds
 * to.
 * @see [ListItem]
 * @see [PopUpTextDisplay]
 * @see [PopUpActonBar]
 */
@Composable
fun ListItemPopUp(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    myTask: MyTask,
    viewModel: TaskViewModel,
) {
    var bgColour = Color.White
    var priorityColour = Color.Black

    if (myTask.checked) {
        bgColour = Color.LightGray
    }
    when (myTask.priority) {
        Priority.NORMAL -> priorityColour = Priority.NORMAL.colour
        Priority.LOW -> priorityColour = Priority.LOW.colour
        Priority.MEDIUM -> priorityColour = Priority.MEDIUM.colour
        Priority.HIGH -> priorityColour = Priority.HIGH.colour
    }

    val width = LocalConfiguration.current.screenWidthDp
    val height = LocalConfiguration.current.screenHeightDp
    val dialogMinWidth = width - (width * 0.5).toInt()
    val dialogMaxWidth = width
    val dialogMaxHeight = height - (height * 0.15).toInt()
    val dialogMinHeight = height - (height * 0.9).toInt()

    Dialog(
        onDismissRequest = { onDismissRequest.invoke() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .border(5.dp, priorityColour, shape = MaterialTheme.shapes.small)
                .widthIn(min = dialogMinWidth.dp, max = dialogMaxWidth.dp)
                .heightIn(min = dialogMinHeight.dp, max = dialogMaxHeight.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = bgColour
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                PopUpTextDisplay(
                    modifier = Modifier,
                    myTask = myTask,
                    priorityColour = priorityColour
                )
                PopUpActonBar(
                    modifier = Modifier,
                    item = myTask,
                    viewModel = viewModel,
                    onDismissRequest = onDismissRequest
                )
            }
        }
    }
}

@Preview
@Composable
fun ListItemPopUpPreview() {
    val item = DummyTodo
    val vm = TaskViewModel(FirebaseRepository())

    Surface(modifier = Modifier.fillMaxSize()) {
        ListItemPopUp(
            modifier = Modifier,
            onDismissRequest = { /*TODO*/ },
            myTask = item,
            viewModel = vm
        )
    }
}