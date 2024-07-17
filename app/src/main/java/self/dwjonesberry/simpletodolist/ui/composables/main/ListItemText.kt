package self.dwjonesberry.simpletodolist.ui.composables.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import self.dwjonesberry.simpletodolist.data.MyTask

@Composable
fun ListItemText(item: MyTask, borderColour: Color) {

    var isStrikeThough by remember { mutableStateOf(false) }

    if (item.checked) {
        isStrikeThough = true
    } else {
        isStrikeThough = false
    }

    Row(verticalAlignment = Alignment.CenterVertically) {
        var str: String = item.id.toString()
        if ((item.id) < 10) {
            str = "0$str"
        }
        if ((item.id) < 100) {
            str = "0$str"
        }
        str = "$str:"
        Column(
            modifier = Modifier
                .background(borderColour)
                .fillMaxHeight()
                .padding(5.dp, 0.dp, 0.dp, 0.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .width(70.dp)
                    .padding(horizontal = 10.dp),
                text = str,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displaySmall,
                fontFamily = FontFamily.Monospace,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier =
            Modifier
                .padding(10.dp, 0.dp, 10.dp, 0.dp)
        )
        {
            Text(
                text = item.title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.displayMedium,
                textDecoration = if (isStrikeThough) TextDecoration.LineThrough else TextDecoration.None,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}