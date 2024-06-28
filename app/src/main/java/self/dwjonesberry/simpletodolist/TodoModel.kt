package self.dwjonesberry.simpletodolist

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.math.max

class TodoModel {

    companion object {

        private val TAG = "MyProject:TodoModel"

        val todoItems: MutableList<TodoItem> =
            mutableListOf(
                TodoItem(0, "Hello", notes = "Welcome to morning!"),
                TodoItem(1, "World", notes = "Na nana NA na na na"),
                TodoItem(2, "Filler", notes = "Link to the past and future"),
                TodoItem(3, "Content", notes = "Glory be!"),
            )
        var maxId: Int =
            try {
                todoItems.last().id + 1
            } catch (e: Exception) {
                Log.d(TAG, "---FAILED to retrieve maxId. maxId set to 0")
                0
            }

//        val todoItems: MutableList<TodoItem> = getFromDatabase()

        fun getFromDatabase(): MutableList<TodoItem> {
            val db: FirebaseFirestore =
                try {
                    Log.d(TAG, "Attempting to get firestore database reference...")
                    com.google.firebase.Firebase.firestore
                } catch (e: Exception) {
                    Log.w(TAG, "---FAILED to get firebase database reference")
                    return mutableListOf<TodoItem>()
                }
            val list = mutableListOf<TodoItem>()

            Log.d(TAG, "Attempting to get collection from database...")
            db.collection("todos")
                .get()
                .addOnSuccessListener { result ->
                    Log.d(TAG, "Attempting to convert results to todo items...")
                    try {
                        for (document in result) {
                            val item = TodoItem(id = document["id"].toString().toInt())
                            Log.d(TAG, "...item id#${item.id}}...")
                            item.text = document["text"].toString()
                            try {
                                item.priority = Priority.valueOf(document["priority"].toString())
                            } catch (e: Exception) {
                                item.priority = Priority.NORMAL
                            }

                            when (document["checked"].toString()) {
                                "true" -> item.checked = true
                                else -> item.checked = false
                            }
                            list.add(item)
                        }
                    } catch (e: Exception) {
                        Log.w(TAG, "---FAILED to convert to todo items", e)
                    }
                }
            return list
        }
    }

    fun addToList(item: String, notes: String) {
        try {
            Log.d(TAG, "Attempting to add new item to list...")
            todoItems.add(TodoItem(maxId, text = item, notes = notes))
            maxId++
        } catch (e: Exception) {
            Log.w(TAG, "---FAILED to add item to todo list", e)
        }
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
        val map: HashMap<String, String> =
            try {
                Log.d(TAG, "ATTEMPTING to create hashmap of id#${todoItem.id}...")
                hashMapOf(
                    "text" to todoItem.text,
                    "priority" to todoItem.priority.name.toString(),
                    "checked" to todoItem.checked.toString()
                )
            } catch (e: Exception) {
                Log.w(TAG, "---FAILED to make hashmap of id#${todoItem.id}", e)
                hashMapOf<String, String>()
            }
        return map
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
        try {
            Log.d(TAG, "ATTEMPTING to delete id#${todoId} from list...")
            val item = todoItems.find {
                it.id == todoId
            }
            todoItems.remove(item)
        } catch (e: Exception) {
            Log.d(TAG, "---FAILED to delete id#${todoId} from list")
        }
    }

    fun updateList(todoItem: TodoItem) {
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