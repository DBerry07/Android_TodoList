package self.dwjonesberry.simpletodolist.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM todo where id=(:pid)")
    fun getItem(pid: Int): Todo

    @Insert
    suspend fun insert(vararg todos: Todo)

    @Update
    suspend fun update(vararg todo: Todo)

    @Delete
    suspend fun delete(vararg todos: Todo)

}