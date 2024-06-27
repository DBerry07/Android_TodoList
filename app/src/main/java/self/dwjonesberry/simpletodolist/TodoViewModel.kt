package self.dwjonesberry.simpletodolist

import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    var text: String = ""
        private set
    private val model: TodoModel = TodoModel()

    val addToList: () -> Unit = {
        model.addToList(text)
        text = ""
    }

    val setText: (String) -> Unit = {
        text = it
    }

    val getItems: () -> MutableList<TodoItem> = {
        TodoModel.todoItems
    }

    val deleteEntry: () -> Unit = {
        model.deleteFromList()
    }

}