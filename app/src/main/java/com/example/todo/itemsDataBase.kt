package com.example.todo

import androidx.compose.ui.graphics.Color
import com.example.todo.ui.theme.*

object SearchItemsList{
    val itemsList = mutableListOf<TodoNote>()
}
object ColorsThemeStateList{
    val itemsList = mapOf(
        "Light White" to Color.White,
        "Dark White" to Black1,
        "Light Yellow" to Yellow300,
        "Dark Yellow" to Yellow500,
        "Light Green" to Green300,
        "Dark Green" to Green800,
        "Light Red" to Red300,
        "Dark Red" to Red900,
        "Light Blue" to Blue300,
        "Dark Blue" to Blue800,
        "Light Magenta" to Magenta300,
        "Dark Magenta" to Magenta800
    )
}
data class TodoNote(val noteTitle:String , val noteDescription:String,val lightColor:Color,val darkColor:Color)