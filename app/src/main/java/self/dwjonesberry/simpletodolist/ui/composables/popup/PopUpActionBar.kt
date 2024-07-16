package self.dwjonesberry.simpletodolist.ui.composables.popup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import self.dwjonesberry.simpletodolist.data.MyTask
import self.dwjonesberry.simpletodolist.data.TaskViewModel

private val TAG: String = "PopUpActionBar"

@Composable
fun PopUpActonBar(
    modifier: Modifier = Modifier,
    item: MyTask,
    viewModel: TaskViewModel,
    onDismissRequest: () -> Unit
) {
    val width = LocalConfiguration.current.screenWidthDp
    val maxWidth = width
    val minWidth = width - (0.5 * width).toInt()
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = {
                Log.d("MyProject", "edit button clicked on item #${item.id}")
                viewModel.navigateToAddScreenWithArguments?.invoke(item)
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
                viewModel.update(item)
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
                viewModel.update(item)
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
            viewModel.delete.invoke(item)
            onDismissRequest.invoke()
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete item ${item.id}."
            )
        }
    }
}