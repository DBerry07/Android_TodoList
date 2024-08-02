# SimpleToDoList
## Native Android App
### By David WJ Berry

SimpleTodoList is a native Android application coded in Kotlin with Firebase Firestore integration.
The app allows the user to create a task, edit a task, delete a task, and mark a task as completed. These tasks appear in lists ("Uncompleted" and "Completed") on the main page. The app also features a "priority" labeling, allowing the user to label a task in one of four priorities - Normal, Low, Medium, and High.
Depending on whether the user has marked a task as "completed", the task appears in either the "Uncompleted" list or "Completed" list.

The app also has sort and filter functions: the user can sort the list based on task ID numbers and sort on the tasks' priority; likewise, the user can use a filter to show only tasks that match the a specific priority.

Currently, the app only supports one login that is shared between all users.

### Technologies Used
- Kotlin
- Firebase Firestore
- Jetpack Compose
- MVVM Design Pattern
- Android Studio
- Android SDK

### Future Plans
- Allow user-created lists, besides just the "Completed" and "Uncompleted" lists.
- Allow the user to add alarms and reminders to tasks.
- Allow users to login based on personal username and password, to show only tasks created by them.
- Allow users to share a task with another user.
