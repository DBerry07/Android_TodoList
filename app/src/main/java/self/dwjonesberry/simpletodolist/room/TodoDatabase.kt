// https://developer.android.com/codelabs/basic-android-kotlin-compose-persisting-data-room

package self.dwjonesberry.simpletodolist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

    companion object {

        private var INSTANCE: TodoDatabase? = null

        fun getInstance(context: Context): TodoDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_database"
                ).build()
            }
            return INSTANCE!!
        }

    }
}