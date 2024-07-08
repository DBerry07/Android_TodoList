package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.ui.graphics.Color
import self.dwjonesberry.simpletodolist.data.Priority
import self.dwjonesberry.simpletodolist.data.TodoItem

fun determineBorder(todoItem: TodoItem): Color {
    return when (todoItem.priority) {
        Priority.NORMAL -> {
            Color.Black
        }

        Priority.LOW -> {
            Color.Green
        }

        Priority.MEDIUM -> {
            Color.Blue
        }

        Priority.HIGH -> {
            Color.Red
        }
    }
}

fun changeBackground(checked: Boolean): Color {
    if (checked) return Color.LightGray
    else return Color.White
}