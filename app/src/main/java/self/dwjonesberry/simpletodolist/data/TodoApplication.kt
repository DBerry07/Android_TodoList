package self.dwjonesberry.simpletodolist.data

import android.app.Application
import self.dwjonesberry.simpletodolist.room.TodoDatabase
import self.dwjonesberry.simpletodolist.room.TodoRepository

class TodoApplication: Application() {

    private val database by lazy { TodoDatabase.getInstance(this) }
    val repository by lazy { TodoRepository(database.todoDao()) }
}