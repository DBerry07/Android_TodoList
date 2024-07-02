package self.dwjonesberry.simpletodolist

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import self.dwjonesberry.simpletodolist.room.Todo
import kotlin.math.max

class TodoModel {

    companion object {

        private val TAG = "MyProject:TodoModel"

//        val todoItems: MutableList<TodoItem> =
//            mutableListOf(
//                TodoItem(0, "Hello", notes = "Welcome to morning!"),
//                TodoItem(1, "World", notes = "Na nana NA na na na"),
//                TodoItem(2, "Filler", notes = "Link to the past and future"),
//                TodoItem(3, "Content", notes = "Glory be!"),
//            )
        val todoItems: MutableList<TodoItem> = mutableListOf()

        init {
            getFromDatabase()
        }

        var maxId: Int = 0

//        val todoItems: MutableList<TodoItem> = getFromDatabase()

        private fun makeTodoItem(text: String, notes: String): TodoItem {
            val item = TodoItem(text = text, notes = notes, id = maxId)
            maxId++
            return item
        }

        // TODO: The list from viewModel still requires the user to reload the screen. Need to fix!
        private fun getFromDatabase() {
            val db: FirebaseFirestore = Firebase.firestore

            Log.d(TAG, "Attempting to get collection from database...")
            db.collection("todos")
                .get()
                .addOnSuccessListener { result ->
                    Log.d(TAG, "Attempting to convert results to todo items...")
                    try {
                        for (document in result) {
                            Log.d(TAG, document.toString())
                            val item = TodoItem(id = document.get("id").toString().toInt())
                            Log.d(TAG, "...item id#${item.id}...")
                            item.text = document["text"].toString()
                            item.notes = document.get("notes").toString()
                            try {
                                item.priority = Priority.valueOf(document.get("priority").toString())
                            } catch (e: Exception) {
                                item.priority = Priority.NORMAL
                            }

                            when (document["checked"].toString()) {
                                "true" -> item.checked = true
                                else -> item.checked = false
                            }
                            Log.v(TAG, item.toString())
                            todoItems.add(item)
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "---FAILED to convert to todo items", e)
                    }

                    try {
                        maxId = todoItems.last().id + 1
                    } catch (e: Exception) {
                        Log.d(TAG, "---FAILED to retrieve maxId. maxId set to 0")
                        maxId = 0
                    }
                }
        }
    }

    fun add(text: String, notes: String) {
        var item = makeTodoItem(text, notes)
        addToList(item)
        addToDatabase(item)
    }

    private fun addToList(item: TodoItem) {
        try {
            Log.d(TAG, "Attempting to add new item to list...")
            todoItems.add(item)
        } catch (e: Exception) {
            Log.w(TAG, "---FAILED to add item to todo list", e)
        }
    }

    private fun addToDatabase(item: TodoItem) {
        val db = Firebase.firestore
        val hashMap = makeHashMap(item)
        db.collection("todos").document(item.id.toString()).set(hashMap)
            .addOnSuccessListener {
                Log.d("MyProject", "added item to database")
            }
            .addOnFailureListener {
                Log.d("MyProject", "failed to add to database", it)
            }
    }

    fun delete(todoItem: TodoItem) {
        deleteFromList(todoItem)
        deleteFromDatabase(todoItem)
    }

    private fun deleteFromDatabase(todo: TodoItem) {
        val db = Firebase.firestore
        db.collection("todos").document(todo.id.toString()).delete()
    }

    private fun makeHashMap(todoItem: TodoItem): HashMap<String, String> {
        val map: HashMap<String, String> =
            try {
                Log.d(TAG, "ATTEMPTING to create hashmap of id#${todoItem.id}...")
                hashMapOf(
                    "id" to todoItem.id.toString(),
                    "text" to todoItem.text,
                    "notes" to todoItem.notes,
                    "priority" to todoItem.priority.name.toString(),
                    "checked" to todoItem.checked.toString()
                )
            } catch (e: Exception) {
                Log.w(TAG, "---FAILED to make hashmap of id#${todoItem.id}", e)
                hashMapOf<String, String>()
            }
        return map
    }

    private fun makeHashMap(string: String): HashMap<String, String> {
        return hashMapOf(
            "text" to string,
            "priority" to Priority.NORMAL.name,
            "checked" to false.toString()
        )
    }


    //todo: does not update existing entries, only adds them
    private fun updateDatabase() {
        val db = Firebase.firestore

        for (item in todoItems) {
            val hashMap = makeHashMap(item)
            db.collection("todos").add(hashMap)
        }
    }

    private fun deleteFromList(todoItem: TodoItem) {
        try {
            Log.d(TAG, "ATTEMPTING to delete id#${todoItem.id} from list...")
            val item = todoItems.find {
                it.id == todoItem.id
            }
            todoItems.remove(item)
        } catch (e: Exception) {
            Log.d(TAG, "---FAILED to delete id#${todoItem.id} from list")
        }
    }

    private fun updateList(todoItem: TodoItem) {
        val db = Firebase.firestore
        val hashMap = makeHashMap(todoItem)
        Log.d(TAG, "ATTEMPTING to add id#${todoItem.id} to database...")
        db.collection("todos").add(hashMap)
            .addOnSuccessListener {
                Log.d(TAG, "...SUCCESSFULLY added item id#${todoItem.id} in database")
            }
            .addOnFailureListener { e: Exception ->
                Log.w(TAG, "---FAILED to add id#${todoItem.id} to database", e)
            }
    }

}