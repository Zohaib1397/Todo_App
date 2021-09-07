package com.example.todo

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme

@Composable
fun LayoutSwitcher(
    currentLayout:String = "Linear View"
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = currentLayout,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.padding(start = 25.dp)
        )
        Row(
            modifier = Modifier.padding(end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {/*TODO(Button Click Listener)*/}) {
                Icon(
                    painter = painterResource(R.drawable.ic_linear_view),
                    contentDescription = "Linear View",
                    tint = Color.Unspecified
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_divider),
                contentDescription = "Layout switcher",
                modifier = Modifier.rotate(90f),
                tint = Color.Unspecified
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_grid_view),
                    contentDescription = "Grid View",
                    tint = Color.Unspecified
                )
            }
        }
    }
}

@Composable
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
    showBackground = true
)
@Preview("Light Mode")
fun PreviewLayoutSwitcher() {
    TodoTheme {
        LayoutSwitcher()
    }
}
