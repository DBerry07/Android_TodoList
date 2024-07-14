package self.dwjonesberry.simpletodolist.data

import android.util.Log

object TaskListRepo {

    val taskMap: MutableMap<String, TaskList> = mutableMapOf()

    fun sort(allTasks: List<MyTask>) {
        taskMap.clear()
        for (each in allTasks) {

            if (each.taskList == "unknown" && each.checked) {
                each.taskList = "completed"
            } else if (each.taskList == "unknown") {
                each.taskList = "uncompleted"
            }

            if (taskMap.containsKey(each.taskList)) {
                taskMap.get(each.taskList)?.taskList?.add(each)
            } else {
                taskMap.put(each.taskList, TaskList())
                taskMap.get(each.taskList)?.heading = each.taskList
                taskMap.get(each.taskList)?.taskList?.add(each)
            }
        }
        for (each in taskMap.keys) {
            Log.d("TaskList", each)
            Log.d("TaskLists", taskMap[each]?.taskList.toString())
        }
    }

    val find: (String) -> TaskList? = { listName ->
        taskMap[listName]
    }

    val delete: (String) -> Boolean = { listName ->
        if (taskMap.remove(listName) != null) {
            true
        } else {
            false
        }
    }

    val addList: (String) -> Boolean = { listName ->
        if (taskMap.containsKey(listName)) {
            false
        } else {
            taskMap.put(listName, TaskList())
            true
        }
    }

    val addTask: (MyTask) -> Unit = { myTask ->
        val taskList = taskMap.get(myTask.taskList)
        taskList?.taskList?.add(myTask)
    }

}