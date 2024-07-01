package self.dwjonesberry.simpletodolist.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import self.dwjonesberry.simpletodolist.Priority

@Entity
data class Todo(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") var text: String = "",
    @ColumnInfo(name = "notes") var notes: String = "",
    @ColumnInfo(name = "checked") var checked: Boolean = false,
    @ColumnInfo(name = "priority") var priority: Priority = Priority.NORMAL
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