package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.ui.graphics.Color
import self.dwjonesberry.simpletodolist.data.Priority
import self.dwjonesberry.simpletodolist.data.TodoItem

fun determineBorder(todoItem: TodoItem): Color {
    return when (todoItem.priority) {
        Priority.NORMAL -> {
            Priority.NORMAL.colour
        }

        Priority.LOW -> {
            Priority.LOW.colour
        }

        Priority.MEDIUM -> {
            Priority.MEDIUM.colour
        }

        Priority.HIGH -> {
            Priority.HIGH.colour
        }
    }
}

fun changeBackground(checked: Boolean): Color {
    if (checked) return Color.LightGray
    else return Color.White
}