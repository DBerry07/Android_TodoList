package self.dwjonesberry.simpletodolist.ui.composables

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import self.dwjonesberry.simpletodolist.data.DummyTodo
import self.dwjonesberry.simpletodolist.data.Priority
import self.dwjonesberry.simpletodolist.data.MyTask


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
 * @param update The lambda function that updates the [MyTask] with new or different information to
 * the database. The changes are automatically displayed in the UI. Executes when the user changes
 * the [MyTask.priority]
 * @param delete The lambda function that deletes a given [MyTask] from the database. Executes when
 * the user presses the "delete" button.
 * @see [ListItem]
 * @see [PopUpTextDisplay]
 * @see [PopUpActonBar]
 */
@Composable
fun ListItemPopUp(
    modifier: Modifier,
    onDismissRequest: () -> Unit,
    myTask: MyTask,
    edit: (MyTask) -> Unit,
    update: (MyTask) -> Unit,
    delete: (MyTask) -> Unit
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

    Dialog(onDismissRequest = { onDismissRequest.invoke() }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        Card(
            modifier = Modifier
                .border(5.dp, priorityColour, shape = MaterialTheme.shapes.small)
                .widthIn(min = dialogMinWidth.dp, max = dialogMaxWidth.dp).heightIn(min = dialogMinHeight.dp, max = dialogMaxHeight.dp),
            colors = CardDefaults.cardColors().copy(
                containerColor = bgColour
            )
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                PopUpTextDisplay(modifier = Modifier, myTask = myTask, priorityColour = priorityColour)
                PopUpActonBar(
                    modifier = Modifier,
                    item = myTask,
                    update = update,
                    delete = delete,
                    edit = edit,
                    onDismissRequest = onDismissRequest
                )
            }
        }
    }
}

/**
 * A [Composable] that contains the text associated with the given [MyTask], namely the item
 * heading and the notes. Used in conjunction with [ListItemPopUp].
 * @param modifier The modifier for this composable.
 * @param myTask The [MyTask] whose contents are displayed by the [PopUpTextDisplay] composable.
 * @param priorityColour The colour associated with the [MyTask.priority]. Used to colour the
 * title text.
 * @see [ListItemPopUp]
 * @see [PopUpActonBar]
 */
@Composable
fun PopUpTextDisplay(modifier: Modifier, myTask: MyTask, priorityColour: Color) {
    Text(myTask.title, fontWeight = FontWeight.Bold, fontSize = 25.sp, color = priorityColour)
    Spacer(modifier = Modifier.padding(5.dp))
    Column(
        modifier = Modifier
            .heightIn(50.dp, 500.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.padding(5.dp))
        Text(myTask.notes, fontSize = 20.sp)
        Spacer(modifier = Modifier.padding(5.dp))
    }
    Spacer(modifier = Modifier.padding(5.dp))
}

/**
 * A [Composable] that contains the buttons that execute various actions related to the given [MyTask].
 * Used in conjunction with [ListItemPopUp].
 * @param modifier The modifier for this [Composable]
 * @param item The [MyTask] associated with the buttons' actions.
 * @param update The lambda function that updates the [MyTask] in the database with new or different
 * information. Currently used when the user changes the [MyTask.priority]. The changes are automatically
 * reflected in the UI.
 * @param delete The lambda function that deletes the [MyTask] from the database. The deletion is
 * automatically reflected in the UI.
 * @param onDismissRequest The actions taken by the app when the [ListItemPopUp] is dismissed by the
 * user. Also dismisses the [ListItemPopUp] when called. In this case, dismisses the dialog when
 * the user deletes the [MyTask].
 */
@Composable
fun PopUpActonBar(
    modifier: Modifier,
    item: MyTask,
    update: (MyTask) -> Unit,
    edit: (MyTask) -> Unit,
    delete: (MyTask) -> Unit,
    onDismissRequest: () -> Unit
) {
    val width = LocalConfiguration.current.screenWidthDp
    val maxWidth = width
    val minWidth = width - (0.5 * width).toInt()
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                Log.d("MyProject", "edit button clicked on item #${item.id}")
                edit.invoke(item)
            }) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit item number ${item.id}"
            )
        }
        IconButton(
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
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Increase priority of item number ${item.id}"
            )
        }
        IconButton(
            onClick = {
                Log.d(
                    TAG,
                    "item #${item.id}: decrease priority button pressed"
                )
                Log.d("MyProject", "current priority: ${item.priority.name}")
                item.decreasePriority()
                update(item)
                Log.d("MyProject", "new priority: ${item.priority.name}")
            }
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrease priority of item number ${item.id}"
            )
        }
        IconButton(onClick = {
            Log.d(TAG, "delete button pressed on item #${item.id}")
            delete.invoke(item)
            onDismissRequest.invoke()
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete item ${item.id}."
            )
        }
    }
}

@Preview
@Composable
fun ListItemPopUpPreview() {
    val item = DummyTodo

    Surface(modifier = Modifier.fillMaxSize()) {
        ListItemPopUp(modifier = Modifier, onDismissRequest = { /*TODO*/ }, myTask = item, {}, {}, {})
    }
}