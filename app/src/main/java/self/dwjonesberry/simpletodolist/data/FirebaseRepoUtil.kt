package self.dwjonesberry.simpletodolist.data

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot

class FirebaseRepoUtil {

    private val TAG: String = "MyProject: FirebaseUtil"

    fun decodeResult(item: DocumentSnapshot): MyTask {
        Log.d(TAG, "Attempting to decode document snapshot...")
        val todo = MyTask(
            id = item.get("id").toString().toInt(),
            title = item.get("text").toString(),
            notes = item.get("notes").toString(),
            priority = Priority.valueOf(item.get("priority").toString()),
            checked = item.get("checked").toString().toBoolean()
        )
//        Log.d(TAG, "todo: ${todo}")
        return todo
    }

    fun makeHashMap(myTask: MyTask): HashMap<String, String> {
        val map: HashMap<String, String> =
            try {
                Log.d(TAG, "ATTEMPTING to create hashmap of id #${myTask.id}...")
                hashMapOf(
                    "id" to myTask.id.toString(),
                    "text" to myTask.title,
                    "notes" to myTask.notes,
                    "priority" to myTask.priority.name.toString(),
                    "checked" to myTask.checked.toString()
                )
            } catch (e: Exception) {
                Log.w(TAG, "---FAILED to make hashmap of id#${myTask.id}", e)
                hashMapOf<String, String>()
            }
        return map
    }

}