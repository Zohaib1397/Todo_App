package com.example.todo

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme

@ExperimentalMaterialApi
@Composable
fun CustomSearchBar(
    placeHolder:String = "Search",
    painter: Painter = painterResource(R.drawable.ic_search),
    contentDescription:String = "Search",
    searchedItems: List<String> = SearchItemsList.itemsList
) {
    var isExpanded by remember { mutableStateOf(true) }
    var searchBarText by remember { mutableStateOf("") }
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = 5.dp
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(30.dp),
            shape = RoundedCornerShape(10.dp),
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
                    modifier = Modifier.onFocusChanged { isExpanded = !isExpanded }
                        .fillMaxWidth(),
                    value = searchBarText,
                    onValueChange = { searchBarText = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = MaterialTheme.colors.onSurface.copy(alpha = 0.2f),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = MaterialTheme.shapes.large,
                    label = {
                        Text(placeHolder)
                    },
                    leadingIcon = {
                        Icon(
                            painter = painter,
                            contentDescription = contentDescription
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            SearchItemsList.itemsList.add(searchBarText)
                        }
                    )
                )
                if (isExpanded) {
                    LazyColumn(){
                        items(searchedItems){ item ->
                            Divider(color = Color.Gray)
                            Text(
                                item,
                                modifier = Modifier.padding(
                                start = 20.dp,
                                top = 12.dp,
                                bottom = 12.dp
                                )
                            )
                        }
                    }
                }
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