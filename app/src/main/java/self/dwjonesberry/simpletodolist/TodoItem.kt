package self.dwjonesberry.simpletodolist

data class TodoItem(
    var text: String = "",
    var checked: Boolean = false,
    var priority: Priority = Priority.NORMAL
)

enum class Priority {
    NORMAL,
    HIGH,
    MEDIUM,
    LOW
}