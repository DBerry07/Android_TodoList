package self.dwjonesberry.simpletodolist.data

import androidx.compose.ui.graphics.Color

enum class Priority(val colour: Color) {
    NORMAL(Color.Black),
    LOW(Color.Green),
    MEDIUM(Color.Blue),
    HIGH(Color.Red),
}