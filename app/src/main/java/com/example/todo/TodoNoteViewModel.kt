package com.example.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TodoNoteViewModel: ViewModel() {
    var TodoNote by mutableStateOf(TodoNotesList.itemsList)
    fun addTodoNote(todo:TodoNote){
        TodoNote = (TodoNote + listOf(todo)).toMutableList()
    }
}