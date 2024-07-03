package self.dwjonesberry.simpletodolist

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepository {

        private val TAG: String = "MyProject: FirebaseRepo"
        private val util = FirebaseRepoUtil()

        fun getAllFromDatabase(): Flow<List<TodoItem>> = callbackFlow {
            val db: FirebaseFirestore = Firebase.firestore
            Log.d(TAG, "Attempting to get collection from database...")

            val listenerRegistration = db.collection("todos")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        // Block executes if error in retrieving from database
                        Log.e(TAG, "Failure: could not get snapshot of database collection", e)
                        close(e)
                    }
                        Log.d(TAG, "Success: got documents from database")
                        val list = mutableListOf<TodoItem>()
                        for (result in snapshot!!.documents) {
                            list.add(util.decodeResult(result))
                        }

                    //Sort is necessary as the largest id is not always in the last position
                    list.sortBy { it.id }

                    //trySend MUST be inside addSnapshotListener, else doubles list on UI
                    trySend(list)
                    }
            awaitClose {  listenerRegistration.remove() }
        }

    /**
     * Retrieve a list of TodoItems from the Firebase Firestore database.
     */



    fun addToDatabase(item: TodoItem) {
        val db = Firebase.firestore
        val hashMap = util.makeHashMap(item)
        db.collection("todos").document(item.id.toString()).set(hashMap)
            .addOnSuccessListener { result ->
                Log.d(TAG, "Success: added item to database w/refID: ${result}")
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Failure: did not add item to database", e)
            }
    }

    fun updateDatabase(todoItem: TodoItem) {
        val db = Firebase.firestore
        val hashMap: Map<String, String> = util.makeHashMap(todoItem)

        db.collection("todos").document(todoItem.id.toString()).update(hashMap)
            .addOnSuccessListener { result ->
                Log.d(TAG, "Success: item updated in database")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Failure: item not updated in database", e)
            }
    }

    fun deleteFromDatabase(todo: TodoItem) {
        val db = Firebase.firestore
        db.collection("todos").document(todo.id.toString()).delete()
    }

}