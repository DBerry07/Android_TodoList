package self.dwjonesberry.simpletodolist

class TodoModel {

    companion object {
        val todoItems: MutableList<String> = getFromDatabase()

        fun getFromDatabase(): MutableList<String> {
            return mutableListOf()
        }
    }

    fun addToList(item: String) {
        todoItems.add(item)
    }

}