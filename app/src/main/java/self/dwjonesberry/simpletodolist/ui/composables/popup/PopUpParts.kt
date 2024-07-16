package self.dwjonesberry.simpletodolist.ui.composables.popup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import self.dwjonesberry.simpletodolist.data.MyTask
import self.dwjonesberry.simpletodolist.data.TaskViewModel

private val TAG: String = "PopUpParts"

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
 * @param onDismissRequest The actions taken by the app when the [ListItemPopUp] is dismissed by the
 * user. Also dismisses the [ListItemPopUp] when called. In this case, dismisses the dialog when
 * the user deletes the [MyTask].
 */