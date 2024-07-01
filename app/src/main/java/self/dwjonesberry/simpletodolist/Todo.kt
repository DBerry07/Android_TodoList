package self.dwjonesberry.simpletodolist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Todo(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") var text: String = "",
    @ColumnInfo(name = "notes") var notes: String = "",
    @ColumnInfo(name = "checked") var checked: Boolean = false,
    @ColumnInfo(name = "priority") var priority: Priority = Priority.NORMAL
)