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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<TodoViewModel>()
    @ExperimentalFoundationApi
    @ExperimentalComposeUiApi
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("TodoNote",Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val todoJsonLoad = sharedPref.getString("todoList",null)
        if(viewModel.todoItems.isEmpty()){
            todoJsonLoad?.let {
                val todoType = object : TypeToken<List<TodoNote>>() {}.type
                val list = Gson().fromJson<List<TodoNote>>(it, todoType)
                list?.let { newList ->
                    for (items in newList) {
                        viewModel.onAddTodo(items)
                    }
                }
            }
        }
        setContent {
            TodoTheme {
                Box(modifier = Modifier.fillMaxSize()){
                    Surface(color = MaterialTheme.colors.background) {
                        CoverWholeScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize()){
                            val todoJson = Gson().toJson(viewModel.todoItems)
                            editor.apply{
                                putString("todoList",todoJson)
                                apply()
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalFoundationApi
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
//            CustomDivider("Pinned Todo")
//            Spacer(modifier = Modifier.height(15.dp))
//            Spacer(modifier = Modifier.height(15.dp))
//            CustomDivider("Unpinned Todo")
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
            saveToSharedPreference = saveToSharedPreference,

        )
    }
}

@ExperimentalFoundationApi
@Composable
fun TodoScreen(
    viewModel:TodoViewModel,
    saveToSharedPreference: () -> Unit
) {
    val scrollState = rememberScrollState()
    val searchedItemsList= mutableListOf<TodoNote>()
    for(todo in viewModel.todoItems){
        if(todo.noteDescription.lowercase().contains(viewModel.searchBarText.lowercase())||todo.noteTitle.lowercase().contains(viewModel.searchBarText.lowercase())){
            searchedItemsList.add(todo)
        }
    }
    LayoutSwitcher(
        currentLayoutState = viewModel.currentTodoLayoutState,
        onLayoutChange = viewModel::onCurrentTodoLayoutChange
    )
    if(viewModel.currentTodoLayoutState == LayoutState.Linear_Layout) {

        LazyColumn(
            contentPadding= PaddingValues(start = 20.dp,end = 20.dp , bottom = 20.dp),
            ) {
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
                    saveToSharedPreference = saveToSharedPreference,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
    else
    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ){
        StaggeredVerticalGrid(
            modifier = Modifier
                .padding(20.dp),
            maxColumnWidth = 300.dp
        ) {
            if (viewModel.searchBarText.isEmpty()) {
                viewModel.todoItems
            } else {
                searchedItemsList
            }.forEachIndexed { index, note ->
                TodoCard(
                    todoTitle = note.noteTitle,
                    todoNote = note.noteDescription,
                    cardColor = if (isSystemInDarkTheme()) note.darkColor else note.lightColor,
                    index = index,
                    todoItems = viewModel.todoItems,
                    onRemoveTodo = viewModel::onRemoveTodo,
                    onEditTodo = viewModel::onTodoEditButton,
                    saveToSharedPreference = saveToSharedPreference,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp, start = 5.dp, end = 5.dp)
                )
            }
        }
    }
}

@ExperimentalFoundationApi
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
fun ShowPreview(){
    TodoTheme {
        Surface(color = MaterialTheme.colors.background) {
            CoverWholeScreen(TodoViewModel(),modifier = Modifier.fillMaxSize()){

            }
        }
    }
}