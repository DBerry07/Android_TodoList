package self.dwjonesberry.simpletodolist.data

import kotlinx.coroutines.flow.StateFlow

interface TaskViewModelInterface: NavigationViewModel {

    val todoList: StateFlow<List<MyTask>>

    var sortedBy: Int

    val add: () -> Unit
    val delete: (MyTask) -> Unit
    val update: (MyTask) -> Unit

}