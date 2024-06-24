package self.dwjonesberry.simpletodolist

import androidx.lifecycle.ViewModel

class TodoViewModel : ViewModel() {

    val todoItems: MutableList<String> = mutableListOf()

}