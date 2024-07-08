package self.dwjonesberry.simpletodolist.screens

import android.util.Log
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import self.dwjonesberry.simpletodolist.DummyTodo
import self.dwjonesberry.simpletodolist.TodoItem


private val TAG: String = "MyProject:PopUp"

@Composable
fun ListItemPopUp(modifier: Modifier, onDismissRequest: () -> Unit, todoItem: TodoItem, update: (TodoItem) -> Unit, delete: (TodoItem) -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Card(modifier = Modifier) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(todoItem.text, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Spacer(modifier = Modifier.padding(5.dp))
                Column(modifier = Modifier.heightIn(50.dp, 500.dp).verticalScroll(rememberScrollState())) {
                    Spacer(modifier = Modifier.padding(5.dp))
                    Text(todoItem.notes, fontSize = 18.sp)
                    Spacer(modifier = Modifier.padding(5.dp))
                }
                Spacer(modifier = Modifier.padding(5.dp))
                PopUpActonBar(modifier = Modifier, item = todoItem, update = update, delete = delete, onDismissRequest = onDismissRequest)
            }
        }
    }
}

@Composable
fun PopUpActonBar(modifier: Modifier, item: TodoItem, update: (TodoItem) -> Unit, delete: (TodoItem) -> Unit, onDismissRequest: () -> Unit) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()) {
        IconButton(
            onClick = {
                Log.d("MyProject", "edit button clicked on item #${item.id}")
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
        ListItemPopUp(modifier = Modifier, onDismissRequest = { /*TODO*/ }, todoItem = item, {}, {})
    }
}