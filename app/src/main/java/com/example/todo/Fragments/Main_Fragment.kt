package com.example.todo.Fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.todo.*
import com.example.todo.ui.theme.TodoTheme

class Main_Fragment : Fragment(){
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
                                Box(
                                    modifier = Modifier.fillMaxSize().padding(20.dp),
                                    contentAlignment = Alignment.BottomEnd
                                ){
                                    CustomFloatingButton()
                                }
                            }
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
                    Box(
                        modifier = Modifier.fillMaxSize().padding(20.dp),
                        contentAlignment = Alignment.BottomEnd
                    ){
                        CustomFloatingButton()
                    }
                }
            }
        }
    }
}