package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import self.dwjonesberry.simpletodolist.data.Priority
import self.dwjonesberry.simpletodolist.data.Sort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(
    navigateToAddToDoScreen: () -> Unit,
    setSortedBy: (Sort) -> Unit,
    setFilterBy: (Priority) -> Unit
) {
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
//            IconButton(onClick = { navigateToAddToDoScreen.invoke() }) {
//                Icon(Icons.Default.Add, "Add a task")
//            }
            Box() {
                Button(
                    onClick = { isSortMenuShown = !isSortMenuShown },
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = Color.Transparent, contentColor = Color.Black)
                ) {
                    Text("SORT")
//                    Icon(Icons.Default.Menu, "Sort menu")
                }
                if (isSortMenuShown) {
                    SortDropDown(toggleSortDropDown, isSortMenuOpen, setSortedBy)
                }
            }

            Box() {
                Button(
                    onClick = { isFilterMenuShown = !isFilterMenuShown },
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = Color.Transparent, contentColor = Color.Black)
                )
                {
                    Text("FILTER")
//                Icon(Icons.Default.Face, "Filter menu")
                }
                if (isFilterMenuShown) {
                    FilterDropDown(toggleFilterDropDown, isFilterMenuOpen, setFilterBy)
                }
            }
        }
        Spacer(modifier = Modifier.padding(horizontal = 10.dp))
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
//                toggleDropDown.invoke()
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
//            toggleDropDown.invoke()
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
//            toggleDropDown.invoke()
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
//            toggleDropDown.invoke()
        })
    }
}

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