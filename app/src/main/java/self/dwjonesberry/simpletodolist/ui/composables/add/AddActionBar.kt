package self.dwjonesberry.simpletodolist.ui.composables.add

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import self.dwjonesberry.simpletodolist.data.TaskViewModel
import self.dwjonesberry.simpletodolist.ui.composables.ActionBar

private val TAG: String = "AddActionBar"

@Composable
fun AddActionBar(
    viewModel: TaskViewModel,
    isEditing: Boolean
) {

    var buttons: List<Pair<@Composable () -> Unit, List<(() -> Unit)?>>>? = null

    if (!isEditing) {
        val row: @Composable () -> Unit = {
            Row() {
                Text("Add")
            }
        }

        buttons = listOf(
            Pair(row, listOf(viewModel.add, viewModel.popBackStack)),
        )
    } else {
        val row: @Composable () -> Unit = {
            Row() {
                Text("Update")
            }
        }
        buttons = listOf(
            Pair(row, listOf(viewModel.updateSelected, viewModel.navigateToMainScreen)),
        )
    }
    ActionBar(buttons = buttons)
}