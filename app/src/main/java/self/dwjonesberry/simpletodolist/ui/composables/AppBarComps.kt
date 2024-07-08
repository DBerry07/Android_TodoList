package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import self.dwjonesberry.simpletodolist.data.Sort

@Composable
fun AppBarDropDown(
    toggleDropDown: () -> Unit,
    getDropDown: () -> Boolean,
    setSortedBy: (Sort) -> Unit
) {
    DropdownMenu(
        expanded = getDropDown.invoke(),
        onDismissRequest = { toggleDropDown.invoke() }
    ) {
        DropdownMenuItem(
            leadingIcon = { Icon(Icons.Default.KeyboardArrowUp, "Sort by ID ascending") },
            text = {
                Text("ID", fontFamily = FontFamily.Monospace)
            },
            onClick = {
                setSortedBy.invoke(Sort.ID_ASC)
                toggleDropDown.invoke()
            })
        DropdownMenuItem(leadingIcon = {
            Icon(
                Icons.Default.KeyboardArrowDown,
                "Sort by ID descending"
            )
        }, text = {
            Text("ID", fontFamily = FontFamily.Monospace)
        }, onClick = {
            setSortedBy.invoke(Sort.ID_DEC)
            toggleDropDown.invoke()
        })
        DropdownMenuItem(leadingIcon = {
            Icon(
                Icons.Default.KeyboardArrowUp,
                "Sort by Priority ascending"
            )
        }, text = {
            Text("Priority", fontFamily = FontFamily.Monospace)
        }, onClick = {
            setSortedBy(Sort.PR_ASC)
            toggleDropDown.invoke()
        })
        DropdownMenuItem(leadingIcon = {
            Icon(
                Icons.Default.KeyboardArrowDown,
                "Sort by Priority descending"
            )
        }, text = {
            Text("Priority", fontFamily = FontFamily.Monospace)
        }, onClick = {
            setSortedBy.invoke(Sort.PR_DEC)
            toggleDropDown.invoke()
        })
    }
}