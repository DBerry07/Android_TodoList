package self.dwjonesberry.simpletodolist.data

import androidx.compose.ui.graphics.Color
import self.dwjonesberry.simpletodolist.ui.theme.myColours

enum class Priority(val colour: Color) {
    NORMAL(myColours.BrightOnPrimary),
    LOW(myColours.BrightPrimary),
    MEDIUM(myColours.BrightTertiary),
    HIGH(myColours.BrightError),
}