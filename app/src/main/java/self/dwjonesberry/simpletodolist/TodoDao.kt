package self.dwjonesberry.simpletodolist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAll(): List<Todo>

    @Query("SELECT 1 FROM todo where id=(:pid)")
    fun getItem(pid: Int): Todo

    @Insert
    fun insert(vararg todos: Todo)

    @Update
    fun update(vararg todo: Todo)

    @Delete
    fun delete(vararg todos: Todo)

}