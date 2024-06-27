package self.dwjonesberry.simpletodolist

class TodoItem(
    var text: String = "",
    var checked: Boolean = false,
    var priority: Priority = Priority.NORMAL
) {
    fun increasePriority() {
        val current = this.priority.ordinal
        if (current < Priority.entries.size - 1) {
            this.priority = Priority.entries[current + 1]
        }
    }
    fun decreasePriority() {
        val current = this.priority.ordinal
        if (current > 0) {
            this.priority = Priority.entries[current - 1]
        }
    }
}

enum class Priority {
    NORMAL,
    LOW,
    MEDIUM,
    HIGH,
}