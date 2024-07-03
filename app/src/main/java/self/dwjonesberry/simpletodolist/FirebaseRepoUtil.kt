package self.dwjonesberry.simpletodolist

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot

class FirebaseRepoUtil {

    private val TAG: String = "MyProject: FirebaseUtil"

    fun decodeResult(item: DocumentSnapshot): TodoItem {
        Log.d(TAG, "Attempting to decode document snapshot...")
        val todo = TodoItem(
            id = item.get("id").toString().toInt(),
            text = item.get("text").toString(),
            notes = item.get("notes").toString(),
            priority = Priority.valueOf(item.get("priority").toString())
        )
//        Log.d(TAG, "todo: ${todo}")
        return todo
    }

    fun makeHashMap(todoItem: TodoItem): HashMap<String, String> {
        val map: HashMap<String, String> =
            try {
                Log.d(TAG, "ATTEMPTING to create hashmap of id #${todoItem.id}...")
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

}