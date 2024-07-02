package self.dwjonesberry.simpletodolist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class TodoViewModel : ViewModel() {

    var text: String = ""
        private set
    var notes: String = ""
        private set
    private val model: TodoModel = TodoModel()

    val add: () -> Unit = {
        model.add(text, notes)
        text = ""
        notes = ""
    }

    val setText: (String) -> Unit = {
        text = it
    }

    val setNotes: (String) -> Unit = {
        notes = it
    }

    val getAll: () -> StateFlow<MutableList<TodoItem>> = {
        TodoModel.todoItems
    }

    val delete: (TodoItem) -> Unit = { todo ->
        model.delete(todo)
    }

    val update: (TodoItem) -> Unit = { todo ->
        model.update(todo)
    }

}