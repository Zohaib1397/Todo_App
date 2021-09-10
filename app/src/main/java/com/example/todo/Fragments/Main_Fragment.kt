package com.example.todo.Fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.todo.*
import com.example.todo.ui.theme.TodoTheme

class Main_Fragment : Fragment() {

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                TodoTheme {
                    Surface(color = MaterialTheme.colors.background) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .animateContentSize(
                                        animationSpec = tween(
                                            durationMillis = 300,
                                            easing = LinearOutSlowInEasing
                                        )
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                            ) {
                                CustomSearchBar(searchedItems = SearchItemsList.itemsList)
                                LayoutSwitcher()
                                CustomDivider("Pinned Todo")
                                Spacer(modifier = Modifier.height(15.dp))
                                Spacer(modifier = Modifier.height(15.dp))
                                CustomDivider("Unpinned Todo")
                                Spacer(modifier = Modifier.height(15.dp))
                                LazyColumn() {
                                    items(TodoNotesList.itemsList) { note ->
                                        Spacer(modifier = Modifier.height(15.dp))
                                        TodoCard(
                                            todoTitle = note.noteTitle,
                                            todoNote = note.noteDescription,
                                            cardColor = if (isSystemInDarkTheme()) note.darkColor else note.lightColor
                                        )
                                    }
                                }

                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            CustomFloatingButton()
                        }
                    }
                }
            }
        }
    }
}

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
            Box(modifier = Modifier.fillMaxSize()){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 300,
                                easing = LinearOutSlowInEasing
                            )
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                ) {
                    CustomSearchBar(searchedItems =SearchItemsList.itemsList)
                    LayoutSwitcher()
                    CustomDivider("Pinned Todo")
                    Spacer(modifier = Modifier.height(15.dp))
                    Spacer(modifier = Modifier.height(15.dp))
                    CustomDivider("Unpinned Todo")
                    Spacer(modifier = Modifier.height(15.dp))
                    LazyColumn(){
                        items(TodoNotesList.itemsList){note->
                            TodoCard(todoTitle =note.noteTitle , todoNote = note.noteDescription,cardColor = if(isSystemInDarkTheme()) note.darkColor else note.lightColor)
                            Spacer(modifier = Modifier.height(15.dp))
                        }
                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.BottomEnd
            ){
                CustomFloatingButton()
            }
        }
    }
}