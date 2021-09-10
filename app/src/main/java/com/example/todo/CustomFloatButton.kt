package com.example.todo

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import com.example.todo.ui.theme.TodoTheme

@Composable
fun CustomFloatingButton(
) {
    //---------- userDefinedColor is for the specific color choose by user from the colors database
    var userDefinedColor:String by remember{mutableStateOf("White")}
    var noteColorAsThemeState = if (isSystemInDarkTheme()) remember { mutableStateOf("Dark") } else remember { mutableStateOf("Light") }

    var isExpended by remember { mutableStateOf(false) }
    var noteColor =
        if (isSystemInDarkTheme()) remember { mutableStateOf(Color.Black.copy(alpha = 0.45f)) } else remember {
            mutableStateOf(Color.White)
        }
    var roundAsState = animateDpAsState(
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        ),
        targetValue = if (isExpended) 30.dp else 50.dp
    )
    var plusColorAsState = animateColorAsState(
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutLinearInEasing
        ),
        targetValue = if (isExpended) if (isSystemInDarkTheme()) Color.White else Color.Black else Color.White
    )
    var buttonRotationAsState = animateFloatAsState(
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearOutSlowInEasing
        ),
        targetValue = if (isExpended) 225f else 0f
    )
    var buttonColorAsState = animateColorAsState(
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutLinearInEasing
        ),
        targetValue = if (isExpended) Color.Unspecified else MaterialTheme.colors.primary
    )
//    var modifierAsState = if(isExpended) Modifier.fillMaxWidth() else Modifier
    Surface(
        shape = RoundedCornerShape(roundAsState.value),
        elevation = 5.dp
    ) {
        //This part of animation will be responsible for having opening transition
        Surface(
            color = buttonColorAsState.value,
            shape = RoundedCornerShape(roundAsState.value),
            modifier = Modifier.animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
//                Box(
//                    modifier = modifierAsState.animateContentSize(
//                        animationSpec = tween(
//                            durationMillis = 300,
//                            easing = LinearOutSlowInEasing
//                        )
//                    ),
//                    contentAlignment = Alignment.TopEnd
//                ){
            if (isExpended) {
                var newNoteTitle by remember { mutableStateOf("") }
                var newNoteText by remember { mutableStateOf("") }
                Surface(
                    color = noteColor.value,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        //----------------- Note/To-do title
                        TextField(
                            value = newNoteTitle, onValueChange = {
                                newNoteTitle = it
                            },
                            placeholder = {
                                Text("Enter Title")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(8.dp),
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        // ----------------------- This text field is for Note/To-do description
                        TextField(
                            value = newNoteText, onValueChange = {
                                newNoteText = it
                            },
                            placeholder = {
                                Text("Enter Todo Note")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(100.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        //------------------ This part of code is used to display Color Buttons in the bottom floating button menu
                        //------------------ Used to Trigger note colors
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            RoundedColorButton(
                                onClick = {
                                    noteColor.value = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} White")!!
                                    userDefinedColor = "White"
                                          },
                                color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} White")!!
                            )
                            RoundedColorButton(
                                onClick = {
                                    noteColor.value = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Yellow")!!
                                    userDefinedColor = "Yellow"
                                          },
                                color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Yellow")!!,
                                contentDescription = "Yellow Color"
                            )
                            RoundedColorButton(
                                onClick = {
                                    noteColor.value = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Green")!!
                                    userDefinedColor = "Green"
                                          },
                                color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Green")!!,
                                contentDescription = "Green Color"
                            )
                            RoundedColorButton(
                                onClick = {
                                    noteColor.value = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Red")!!
                                    userDefinedColor = "Red"
                                          },
                                color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Red")!!,
                                contentDescription = "Red Color"
                            )
                            RoundedColorButton(
                                onClick = {
                                    noteColor.value = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Blue")!!
                                    userDefinedColor = "Blue"
                                          },
                                color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Blue")!!,
                                contentDescription = "Blue Color"
                            )
                            RoundedColorButton(
                                onClick = {
                                    noteColor.value = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Magenta")!!
                                    userDefinedColor = "Magenta"
                                          },
                                color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Magenta")!!,
                                contentDescription = "Magenta Color"
                            )

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        //------------ Save button to load the data to database
                        Button(
                            onClick = {
                                      TodoNotesList.itemsList.add(TodoNote(newNoteTitle,newNoteText,ColorsThemeStateList.itemsList.get("Light ${userDefinedColor}")!!,ColorsThemeStateList.itemsList.get("Dark ${userDefinedColor}")!!))
                                        isExpended = !isExpended
                                      },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(45.dp)
                        ) {
                            Text("Save")
                        }
                    }
                }
            }
            IconButton(
                onClick = { isExpended = !isExpended },
                modifier = Modifier.rotate(buttonRotationAsState.value),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = "Add Todo",
                    tint = plusColorAsState.value
                )
            }
//                }
        }
    }
}


@Composable
fun RoundedColorButton(
    onClick: () -> Unit,
    borderThickness: BorderStroke = BorderStroke(1.dp, Color.Gray),
    color: Color = Color.Unspecified,
    contentDescription: String = "White Color"
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_filled_circle),
            contentDescription = contentDescription,
            tint = color,
            modifier = Modifier.border(
                border = borderThickness,
                shape = CircleShape
            )
        )
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun previewFloatingButton() {
    TodoTheme(){
        Surface(color = MaterialTheme.colors.surface){
            CustomFloatingButton()
        }
    }
}