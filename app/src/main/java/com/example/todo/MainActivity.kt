package com.example.todo

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.Fragments.Main_Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,Main_Fragment()).commit()


    }
}
object saveToSharedPreference :AppCompatActivity(){
    val sharedPref = getSharedPreferences("SearchBar",Context.MODE_PRIVATE)
    val editor = sharedPref.edit().also{
        it.putStringSet("searchedItems",SearchItemsList.itemsList.toSet())
    }
}
object loadFromSharedPreference:AppCompatActivity(){
    val sharedPref = getSharedPreferences("SearchBar", Context.MODE_PRIVATE)
}