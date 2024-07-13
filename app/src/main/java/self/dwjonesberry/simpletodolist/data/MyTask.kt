package self.dwjonesberry.simpletodolist.data

class MyTask(
    val id: Int,
    var title: String = "",
    var notes: String = "",
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

    override fun toString(): String {
        return "TODO:: id:${id.toString()}, text:${title}, notes:${notes}, priority:${priority.name.toString()}"
    }
}