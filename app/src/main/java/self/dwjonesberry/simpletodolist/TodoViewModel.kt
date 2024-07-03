package self.dwjonesberry.simpletodolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repo: FirebaseRepository) : ViewModel() {

    var text: String = ""
        private set
    var notes: String = ""
        private set
    private val _todoList = MutableStateFlow<List<TodoItem>>(emptyList())
    val todoList: StateFlow<List<TodoItem>> = _todoList.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAllFromDatabase().collect { newList ->
                _todoList.value = newList
            }
        }
    }

    val maxId: Int
        get() = _todoList.value.last().id + 1

    val add: () -> Unit = {
        val item = TodoItem(id = maxId, text = text, notes = notes)
        repo.addToDatabase(item)
        text = ""
        notes = ""
    }

    val setText: (String) -> Unit = {
        text = it
    }

    val setNotes: (String) -> Unit = {
        notes = it
    }

    val delete: (TodoItem) -> Unit = { todo ->
        repo.deleteFromDatabase(todo)
    }

    val update: (TodoItem) -> Unit = { todo ->
        repo.updateDatabase(todo)
    }

}

class TodoViewModelFactory(private val repository: FirebaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}