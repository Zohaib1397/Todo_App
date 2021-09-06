package com.example.todo

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme

@ExperimentalMaterialApi
@Composable
fun CustomSearchBar() {
    var isExpanded by remember { mutableStateOf(true) }
    var searchBarText by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier.fillMaxWidth(0.9f),
        shape = MaterialTheme.shapes.small,
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
            TextField(
                modifier = Modifier.onFocusChanged { isExpanded = !isExpanded },
                value = searchBarText,
                onValueChange = { searchBarText = it },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.large,
                label = {
                    Text("Search")
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search"
                    )
                }
            )
            if (isExpanded) {
                Divider(color = Color.Gray)
                Text(
                    "Sample Text",
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    )
                )
                //TODO("Show List of Previous Search")
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
    showBackground = true
)
@Preview("Light Mode")
@Composable
fun previewSearchBar() {
    TodoTheme {
        CustomSearchBar()
    }
}