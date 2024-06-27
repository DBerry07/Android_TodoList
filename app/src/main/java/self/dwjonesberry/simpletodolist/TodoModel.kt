package self.dwjonesberry.simpletodolist

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.max

class TodoModel {

    companion object {
        val todoItems: MutableList<TodoItem> =
            mutableListOf(
                TodoItem(0, "Hello"),
                TodoItem(1, "World"),
                TodoItem(2, "Filler"),
                TodoItem(3, "Content"),
            )
        var maxId = todoItems.last().id + 1

//        val todoItems: MutableList<TodoItem> = getFromDatabase()

        fun getFromDatabase(): MutableList<TodoItem> {
            val db = Firebase.firestore
            val list = mutableListOf<TodoItem>()
            db.collection("todos")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val item = TodoItem(id = document["id"].toString().toInt())
                        item.text = document["text"].toString()
                        try {
                            item.priority = Priority.valueOf(document["priority"].toString())
                        } catch(e: Exception) {
                            item.priority = Priority.NORMAL
                        }

                        when (document["checked"].toString()) {
                            "true" -> item.checked = true
                            else -> item.checked = false
                        }

                        list.add(item)
                    }
                }
            return list
        }
    }

    fun addToList(item: String) {
        todoItems.add(TodoItem(maxId, text = item))
        maxId++
//
//        val db = Firebase.firestore
//        val hashMap = hashMapOf(
//            "text" to item,
//            "priority" to Priority.NORMAL,
//            "checked" to false,
//        )
//        db.collection("todos").add(hashMap)
//            .addOnSuccessListener {
//                Log.d("MyProject", "added item to database")
//            }
//            .addOnFailureListener {
//                Log.d("MyProject", "failed to add to database", it)
//            }
    }

    fun makeHashMap(todoItem: TodoItem): HashMap<String, String> {
        return hashMapOf(
            "text" to todoItem.text,
            "priority" to todoItem.priority.name.toString(),
            "checked" to todoItem.checked.toString()
        )
    }

    fun makeHashMap(string: String): HashMap<String, String> {
        return hashMapOf(
            "text" to string,
            "priority" to Priority.NORMAL.name,
            "checked" to false.toString()
        )
    }


    //todo: does not update existing entries, only adds them
    fun updateDatabase() {
        val db = Firebase.firestore

        for (item in todoItems) {
            val hashMap = makeHashMap(item)
            db.collection("todos").add(hashMap)
        }
    }

    fun deleteFromList(todoId: Int) {
        val item = todoItems.find {
            it.id == todoId
        }
        todoItems.remove(item)
    }

    fun updateList(todoItem: TodoItem) {
        val db = Firebase.firestore
        val hashMap = makeHashMap(todoItem)
        db.collection("todos").add(hashMap)
            .addOnSuccessListener {
                Log.d("MyProject", "updated item in database")
            }
            .addOnFailureListener {
                Log.d("MyProject", "failed to update to database", it)
            }
    }

}