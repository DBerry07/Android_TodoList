package self.dwjonesberry.simpletodolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import self.dwjonesberry.simpletodolist.ui.composables.MyApp
import self.dwjonesberry.simpletodolist.ui.theme.SimpleToDoListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SimpleToDoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyApp()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
//        val model = TodoModel()
//        model.updateDatabase()
    }
}