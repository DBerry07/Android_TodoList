package self.dwjonesberry.simpletodolist.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskViewModel(private val repo: FirebaseRepository) : ViewModel(), TaskViewModelInterface {

    var title: String = ""
        private set
    var notes: String = ""
        private set
    var priority: Priority = Priority.NORMAL
        private set
    var id: Int = 0
        private set
    var selectedTodo: MyTask? = null
        set(value) {
            field = value
            if (value != null) {
                title = value.title
                notes = value.notes
                priority = value.priority
                id = value.id

            }
            else {
                title = ""
                notes = ""
                priority = Priority.NORMAL
                id = maxId
            }
        }

    override var navigateToMainScreen: (() -> Unit)? = null
    override var navigateToAddScreenWithArguments: ((MyTask?) -> Unit)? = null
    override var popBackStack: (() -> Unit)? = null

    private val _todoList = MutableStateFlow<List<MyTask>>(emptyList())
    override val todoList: StateFlow<List<MyTask>> = _todoList.asStateFlow()
    override var sortedBy = 0

    init {
        viewModelScope.launch {
            repo.getAllFromDatabase().collect { newList ->
                if (_todoList.value.isEmpty()) {
                    _todoList.value = newList
                } else {
                    _todoList.value = newList
                }
            }
        }
    }

    val maxId: Int
        get() = _todoList.value.last().id + 1

    fun incrementSortedBy() {
        sortedBy += 1
        if (sortedBy > 3) sortedBy = 0
    }

    val setSortedBy: (Sort) -> Unit = {
        sortedBy = it.ordinal
        sort.invoke()
    }

    val sort: () -> Unit = {
        when(sortedBy) {
            0 -> _todoList.value = _todoList.value.sortedBy { it.id }.sortedBy { it.checked }
            1 -> _todoList.value = _todoList.value.sortedByDescending { it.id }.sortedBy { it.checked }
            2 -> _todoList.value = _todoList.value.sortedBy { it.priority.ordinal }.sortedBy { it.checked }
            3 -> _todoList.value = _todoList.value.sortedByDescending { it.priority.ordinal }.sortedBy { it.checked }
            else -> _todoList.value = _todoList.value.sortedBy{ it.id }.sortedBy { it.checked }
        }
    }

    override val add: () -> Unit = {
        val item = MyTask(id = maxId, title = title, notes = notes)
        repo.addToDatabase(item)
        title = ""
        notes = ""
    }

    val setText: (String) -> Unit = {
        title = it
    }

    val setNotes: (String) -> Unit = {
        notes = it
    }

    override val delete: (MyTask) -> Unit = { todo ->
        repo.deleteFromDatabase(todo)
    }

    override val update: (MyTask) -> Unit = { todo ->
        repo.updateDatabase(todo)
    }

    val updateSelected: () -> Unit = {
        val todoItem = selectedTodo
        if (todoItem != null) {
            todoItem.title = title
            todoItem.notes = notes
            repo.updateDatabase(todoItem)
        }
    }

}

class TaskViewModelFactory(private val repository: FirebaseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}