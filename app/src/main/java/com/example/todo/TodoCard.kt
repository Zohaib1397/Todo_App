package com.example.todo

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme

private enum class Expanded{
    True,
    False
}

@Composable
fun TodoCard(
    todoTitle:String,
    todoNote:String,
    cardColor: Color = MaterialTheme.colors.surface,
    index:Int,
    modifier: Modifier = Modifier,
    todoItems:List<TodoNote>,
    onRemoveTodo:(TodoNote) -> Unit,
    onEditTodo:(String,String) -> Unit,
    saveToSharedPreference:() -> Unit,
){
    var isExpanded by remember{mutableStateOf(Expanded.False)}
    val rotateState by animateFloatAsState(
        if(isExpanded == Expanded.True) 180f else 0f
    )
    Card(
        modifier = modifier.clickable {
             isExpanded = when(isExpanded){
                 Expanded.False -> Expanded.True
                 Expanded.True -> Expanded.False
             }
        },
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .background(color = cardColor)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
        ){
            Text(
                text = todoTitle,
                fontWeight = FontWeight(700),
                modifier = Modifier.padding(start = 20.dp,top = 12.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = todoNote,
                    modifier = Modifier.padding(start = 20.dp,bottom = 12.dp).fillMaxWidth(0.8f)
                )
                IconButton(onClick = { isExpanded = when(isExpanded){
                    Expanded.False -> Expanded.True
                    Expanded.True -> Expanded.False
                } }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_down),
                        contentDescription = "DropDown",
                        modifier =Modifier.rotate(rotateState)
                    )
                }
            }
            if(isExpanded == Expanded.True){
                Divider(modifier =Modifier.fillMaxWidth(),color = Color.Gray)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    IconButton(onClick = {
                        onEditTodo(todoTitle,todoNote)
                        onRemoveTodo(todoItems[index])
                        saveToSharedPreference()
                    }){
                        Icon(painter = painterResource(R.drawable.ic_edit), contentDescription = "Edit Todo")
                    }
                    IconButton(onClick = {
                        onRemoveTodo(todoItems[index])
                        saveToSharedPreference()
                    }){
                        Icon(painter = painterResource(R.drawable.ic_delete), contentDescription = "Delete Todo")
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
fun previewTodoCard() {
    TodoTheme {
        Box(modifier = Modifier.fillMaxSize()){
            TodoCard(
                "Todo Title",
                "This is a sample Todo long long todo Note ever ever ever long note hihihihi",
                ColorsThemeStateList.itemsList.get("Light Yellow")!!,
                0,
                todoItems = listOf(
                    TodoNote(
                        "Todo Title",
                        "This is a sample Todo",
                        ColorsThemeStateList.itemsList.get("Light Yellow")!!,
                        ColorsThemeStateList.itemsList.get("Dark Yellow")!!
                    )
                ),
                onRemoveTodo = {},
                saveToSharedPreference = {},
                onEditTodo = fun(title: String, text: String) {}
            )
        }
    }
}