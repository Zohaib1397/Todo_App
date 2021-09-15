package com.example.todo

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {
    //state
    //This state is for LayoutSwithcer
    var currentTodoLayoutState by mutableStateOf(true)
    //This State is for the list of searched Items
    var searchItemsList = mutableStateListOf<String>()
    //This state is for text inside Search bar
    var searchBarText by mutableStateOf("")
    //This state is for a todoList
    var todoItems = mutableStateListOf<TodoNote>()



    //event
    fun onCurrentTodoLayoutChange(){
        currentTodoLayoutState = !currentTodoLayoutState
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
}