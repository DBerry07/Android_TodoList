package self.dwjonesberry.simpletodolist

import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    //TODO: try to save items without using companion object
    companion object {
        val _todoItems: MutableList<String> = mutableListOf()
    }

    val todoItems = _todoItems

    var text: String = ""

    val addToList: () -> Unit = {
        _todoItems.add(text)
        text = ""
    }

    val setText: (String) -> Unit = {
        text = it
    }

}