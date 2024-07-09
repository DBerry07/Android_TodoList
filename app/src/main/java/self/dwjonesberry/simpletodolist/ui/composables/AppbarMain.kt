package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontFamily
import self.dwjonesberry.simpletodolist.data.Priority
import self.dwjonesberry.simpletodolist.data.Sort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(navigateToAddToDoScreen: () -> Unit, setSortedBy: (Sort) -> Unit, setFilterBy: (Priority) -> Unit) {
    var isSortMenuShown by remember { mutableStateOf(false) }
    var isFilterMenuShown by remember { mutableStateOf(false) }

    val isSortMenuOpen: () -> Boolean = {
        isSortMenuShown
    }
    val isFilterMenuOpen: () -> Boolean = {
        isFilterMenuShown
    }
    val toggleSortDropDown: () -> Unit = {
        isSortMenuShown = !isSortMenuShown
    }
    val toggleFilterDropDown: () -> Unit = {
        isFilterMenuShown = !isFilterMenuShown
    }
    TopAppBar(title = { Text("Task List") }, actions = {
        Row() {
            IconButton(onClick = { navigateToAddToDoScreen.invoke() }) {
                Icon(Icons.Default.Add, "Add a task")
            }
            IconButton(onClick = { isSortMenuShown = !isSortMenuShown }) {
                Icon(Icons.Default.Menu, "Sort menu")
            }
            IconButton(onClick = { isFilterMenuShown = !isFilterMenuShown}) {
                Icon(Icons.Default.Face, "Filter menu")
            }
            if (isSortMenuShown) {
                SortDropDown(toggleSortDropDown, isSortMenuOpen, setSortedBy)
            }
            if (isFilterMenuShown) {
                FilterDropDown(toggleFilterDropDown, isFilterMenuOpen, setFilterBy)
            }
        }
    })
}

@Composable
fun SortDropDown(
    toggleDropDown: () -> Unit,
    isMenuShown: () -> Boolean,
    setSortedBy: (Sort) -> Unit
) {
    DropdownMenu(
        expanded = isMenuShown.invoke(),
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

@Composable
fun FilterDropDown(
    toggleDropDown: () -> Unit,
    getDropDown: () -> Boolean,
    setFilterBy: (Priority) -> Unit,
) {
    DropdownMenu(expanded = getDropDown.invoke()
        , onDismissRequest = { toggleDropDown.invoke() })
    {
        DropdownMenuItem(text = { Text("Normal") }, onClick = { setFilterBy.invoke(Priority.NORMAL) })
        DropdownMenuItem(text = { Text("Low") }, onClick = { setFilterBy.invoke(Priority.LOW) })
        DropdownMenuItem(text = { Text("Medium") }, onClick = { setFilterBy.invoke(Priority.MEDIUM) })
        DropdownMenuItem(text = { Text("High") }, onClick = { setFilterBy.invoke(Priority.HIGH)})
    }
}