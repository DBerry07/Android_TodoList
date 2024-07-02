package self.dwjonesberry.simpletodolist

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        //TODO: get more detailed knowledge of how state flows work
        private val _todoItems = MutableStateFlow<MutableList<TodoItem>>(mutableListOf<TodoItem>())
        val todoItems: StateFlow<MutableList<TodoItem>> get() = _todoItems

        var maxId: Int = 0

        init {
            getFromDatabase()
        }

        private fun makeTodoItem(text: String, notes: String): TodoItem {
            val item = TodoItem(text = text, notes = notes, id = maxId)
            maxId++
            return item
        }

        private fun getFromDatabase() {
            val db: FirebaseFirestore = Firebase.firestore

            Log.d(TAG, "Attempting to get collection from database...")
            db.collection("todos")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        Log.e(TAG, "Failure: could not get snapshot of database collection", e)
                        //todo: handle the error
                    }
                    else if (snapshot != null && !snapshot.isEmpty) {
                        val list = mutableListOf<TodoItem>()
                        for (item in snapshot.documents) {
                            val todo = TodoItem(
                                id = item.get("id").toString().toInt(),
                                text = item.get("text").toString(),
                                notes = item.get("notes").toString(),
                                priority = Priority.valueOf(item.get("priority").toString())
                                )
                            list.add(todo)
                        }
                        _todoItems.value = list
                        maxId = _todoItems.value.last().id + 1
                    }
                }
        }
    }

    fun add(text: String, notes: String) {
        val item = makeTodoItem(text, notes)
//        addToList(item)
        addToDatabase(item)
    }

    private fun addToList(item: TodoItem) {
//        _todoItems.value.add(item)
    }

    private fun addToDatabase(item: TodoItem) {
        val db = Firebase.firestore
        val hashMap = makeHashMap(item)
        db.collection("todos").document(item.id.toString()).set(hashMap)
            .addOnSuccessListener { result ->
                Log.d(TAG, "Success: added item to database w/refID: ${result}")
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Failure: did not add item to database", e)
            }
    }

    fun delete(todoItem: TodoItem) {
//        deleteFromList(todoItem)
//        deleteFromDatabase(todoItem)
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
//    private fun updateDatabase() {
//        val db = Firebase.firestore
//
//        for (item in _todoItems.value) {
//            val hashMap = makeHashMap(item)
//            db.collection("todos").add(hashMap)
//        }
//    }

//    private fun deleteFromList(todoItem: TodoItem) {
//        try {
//            Log.d(TAG, "ATTEMPTING to delete id#${todoItem.id} from list...")
//            val item = todoItems.find {
//                it.id == todoItem.id
//            }
//            todoItems.remove(item)
//        } catch (e: Exception) {
//            Log.d(TAG, "---FAILED to delete id#${todoItem.id} from list")
//        }
//    }

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