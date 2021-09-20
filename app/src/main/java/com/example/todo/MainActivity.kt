package com.example.todo

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<TodoViewModel>()
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("TodoNote",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val TodoJsonLoad = sharedPref.getString("todoList",null)
        TodoJsonLoad?.let{
            val TodoType = object : TypeToken<List<TodoNote>>() {}.type
            val list = Gson().fromJson<List<TodoNote>>(it, TodoType)
            list?.let{ newList ->
                for (items in newList) {
                    viewModel.onAddTodo(items)
                }
            }
        }
        setContent {
            TodoTheme {
                Box(modifier = Modifier.fillMaxSize()){
                    Surface(color = MaterialTheme.colors.background) {
                        CoverWholeScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize()){
                            val TodoJson = Gson().toJson(viewModel.todoItems)
                            editor.apply{
                                putString("todoList",TodoJson)
                                apply()
                            }
                        }
                    }
                }
            }
        }


    }

}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun CoverWholeScreen(
    viewModel:TodoViewModel,
    modifier:Modifier = Modifier,
    saveToSharedPreference:() -> Unit
){
    Scaffold(
        modifier = modifier
    ){
        Column(
            modifier = modifier.animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ){
            CustomSearchBar(
                searchedItems = viewModel.searchItemsList,
                onAddSearchItem = viewModel::onAddSearch,
                searchBarText = viewModel.searchBarText,
                onSearchBarTextChange = viewModel::onSearchBarTextChange
            )
            LayoutSwitcher(
                currentLayoutState = viewModel.currentTodoLayoutState,
                onLayoutChange = viewModel::onCurrentTodoLayoutChange
            )
//            CustomDivider("Pinned Todo")
//            Spacer(modifier = Modifier.height(15.dp))
//            Spacer(modifier = Modifier.height(15.dp))
//            CustomDivider("Unpinned Todo")
            Spacer(modifier = Modifier.height(15.dp))
            TodoScreen(
                viewModel,
                saveToSharedPreference = saveToSharedPreference
            )
        }
        CustomFloatingButton(
            isExpanded = viewModel.isTodoExpanded,
            onIsExpandedChange = viewModel::onTodoExpandedChange,
            onAddTodoItem = viewModel::onAddTodo,
            newNoteTitle = viewModel.editTodoTitle,
            newNoteText = viewModel.editTodoText,
            onSetNewNoteTitle = viewModel::onEditTodoTitle,
            onSetNewNoteText = viewModel::onEditTodoText,
            saveToSharedPreference = saveToSharedPreference
        )
    }
}

@Composable
fun TodoScreen(
    viewModel:TodoViewModel,
    saveToSharedPreference: () -> Unit
) {
    val searchedItemsList= mutableListOf<TodoNote>()
    for(todo in viewModel.todoItems){
        if(todo.noteDescription.contains(viewModel.searchBarText.toString())||todo.noteTitle.contains(viewModel.searchBarText.toString())){
            searchedItemsList.add(todo)
        }
    }
    LazyColumn() {
        itemsIndexed(
            if(viewModel.searchBarText.isEmpty())viewModel.todoItems else searchedItemsList
                ) { index,note ->
            Spacer(modifier = Modifier.height(15.dp))
            TodoCard(
                todoTitle = note.noteTitle,
                todoNote = note.noteDescription,
                cardColor = if (isSystemInDarkTheme()) note.darkColor else note.lightColor,
                index = index,
                todoItems = viewModel.todoItems,
                onRemoveTodo = viewModel::onRemoveTodo,
                onEditTodo = viewModel::onTodoEditButton,
                saveToSharedPreference = saveToSharedPreference
            )
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
@Preview(name = "Light Mode",showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
fun showPreview(){
    TodoTheme {
        Surface(color = MaterialTheme.colors.background) {
            CoverWholeScreen(TodoViewModel(),modifier = Modifier.fillMaxSize()){

            }
        }
    }
}