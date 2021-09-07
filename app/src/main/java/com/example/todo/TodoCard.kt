package com.example.todo

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme

@Composable
fun TodoCard(){
    var isExpanded by remember {mutableStateOf(false)}
    val rotateState by animateFloatAsState(
        if(isExpanded) 180f else 0f
    )
    Card(
        //TODO(Enable This Comment)
//        modifier = Modifier.fillMaxWidth(0.9f),
        shape = RoundedCornerShape(12.dp),
        elevation = 5.dp
    ) {
        Column(){
            Text(
                text = "Notes Title",
                fontWeight = FontWeight(700),
                modifier = Modifier.padding(start = 20.dp,top = 12.dp)
            )
//            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "This is the sample Text",
                    modifier = Modifier.padding(start = 20.dp)
                )
                IconButton(onClick = {
                    isExpanded = !isExpanded
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = "DropDown",
                        modifier =Modifier.rotate(rotateState)
                    )
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
fun previewTodoCard() {
    TodoTheme {
        TodoCard()
    }
}