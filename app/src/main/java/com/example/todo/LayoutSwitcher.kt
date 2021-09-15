package com.example.todo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme

@Composable
fun LayoutSwitcher(
    currentLayoutState: Boolean,
    onLayoutChange:() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 25.dp,end = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = if(currentLayoutState)"Linear View" else "Gird View",
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
        )
        IconButton(onClick = onLayoutChange  ) {
            Icon(
                painter = if(currentLayoutState)
                    painterResource(
                        R.drawable.ic_linear_view
                    )else painterResource(
                    id = R.drawable.ic_grid_view
                ),
                contentDescription = if(currentLayoutState)"Linear View" else "Gird View",
                tint = Color.Unspecified
            )
        }
    }
}

@Composable
@Preview("Light Mode")
fun PreviewLayoutSwitcher() {
    TodoTheme {
        LayoutSwitcher(true,{})
    }
}
