package self.dwjonesberry.simpletodolist

import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    val todoItems: MutableList<String> = mutableListOf()

    var text: String = ""

    val addToList: () -> Unit = {
        todoItems.add(text)
        text = ""
    }

    val setText: (String) -> Unit = {
        text = it
    }

}