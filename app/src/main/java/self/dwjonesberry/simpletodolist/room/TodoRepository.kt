package self.dwjonesberry.simpletodolist.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    val allItems: List<Todo> = todoDao.getAll()

    @WorkerThread
    suspend fun insertItem(todo: Todo) {
        todoDao.insert(todo)
    }

}