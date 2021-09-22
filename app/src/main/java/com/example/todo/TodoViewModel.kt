package com.example.todo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

enum class LayoutState{
    Grid_Layout,
    Linear_Layout
}

class TodoViewModel: ViewModel() {
    //state
    //This state is for LayoutSwithcer
    var currentTodoLayoutState by mutableStateOf(LayoutState.Linear_Layout)
    //This State is for the list of searched Items
    var searchItemsList = mutableStateListOf<String>()
    //This state is for text inside Search bar
    var searchBarText by mutableStateOf("")
    //This state is for a todoList
    var todoItems = mutableStateListOf<TodoNote>(
//        TodoNote("Todo Title","This is a sample Todo",ColorsThemeStateList.itemsList.get("Light Yellow")!!,ColorsThemeStateList.itemsList.get("Dark Yellow")!!),
//        TodoNote("Todo Title","This is a sample Todo",ColorsThemeStateList.itemsList.get("Light Blue")!!,ColorsThemeStateList.itemsList.get("Dark Blue")!!),
//        TodoNote("Todo Title","This is a sample Todo",ColorsThemeStateList.itemsList.get("Light Red")!!,ColorsThemeStateList.itemsList.get("Dark Red")!!),
//        TodoNote("Todo Title","This is a sample Todo",ColorsThemeStateList.itemsList.get("Light Green")!!,ColorsThemeStateList.itemsList.get("Dark Green")!!)
    )

    var isTodoExpanded by mutableStateOf(false)

    var editTodoTitle by mutableStateOf("")
    var editTodoText by mutableStateOf("")


    //event
    fun onCurrentTodoLayoutChange(){
        currentTodoLayoutState = when(currentTodoLayoutState){
            LayoutState.Linear_Layout -> LayoutState.Grid_Layout
            LayoutState.Grid_Layout -> LayoutState.Linear_Layout
        }
    }
    fun onAddSearch(data:String){
        searchItemsList += data
    }
    fun onSearchBarTextChange(searchText:String){
        searchBarText = searchText
    }
    fun onAddTodo(todo:TodoNote){
        todoItems+=todo
    }
    fun onRemoveTodo(todo:TodoNote){
        todoItems-=todo
    }
    fun onTodoExpandedChange(){
        isTodoExpanded = !isTodoExpanded
    }
    fun onEditTodoTitle(title:String){
        editTodoTitle = title
    }
    fun onEditTodoText(text:String){
        editTodoText= text
    }
    fun onTodoEditButton(title:String , text:String){
        isTodoExpanded = !isTodoExpanded
        editTodoTitle = title
        editTodoText = text
    }
}