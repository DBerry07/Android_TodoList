package self.dwjonesberry.simpletodolist.ui.composables.main

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import self.dwjonesberry.simpletodolist.data.Priority

@Composable
fun FilterDropDown(
    toggleDropDown: () -> Unit,
    getDropDown: () -> Boolean,
    setFilterBy: (Priority) -> Unit,
) {
    DropdownMenu(expanded = getDropDown.invoke(), onDismissRequest = { toggleDropDown.invoke() })
    {
        DropdownMenuItem(
            text = { Text("NONE", color = Priority.NORMAL.colour) },
            onClick = { setFilterBy.invoke(Priority.NORMAL) })
        DropdownMenuItem(
            text = { Text("LOW", color = Priority.LOW.colour) },
            onClick = { setFilterBy.invoke(Priority.LOW) })
        DropdownMenuItem(
            text = { Text("MEDIUM", color = Priority.MEDIUM.colour) },
            onClick = { setFilterBy.invoke(Priority.MEDIUM) })
        DropdownMenuItem(
            text = { Text("HIGH", color = Priority.HIGH.colour) },
            onClick = { setFilterBy.invoke(Priority.HIGH) })
    }
}