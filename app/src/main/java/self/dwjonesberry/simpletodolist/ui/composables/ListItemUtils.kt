package self.dwjonesberry.simpletodolist.ui.composables

import androidx.compose.ui.graphics.Color
import self.dwjonesberry.simpletodolist.data.Priority
import self.dwjonesberry.simpletodolist.data.MyTask

fun determineBorder(myTask: MyTask): Color {
    return when (myTask.priority) {
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