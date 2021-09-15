package com.example.todo

import android.content.res.Configuration
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
//Following Composable Function is StateFull
fun CustomFloatingButton(
    onAddTodoItem:(TodoNote)->Unit
) {
    var isExpanded by remember { mutableStateOf(true) }
    var alignment = animateFloatAsState(
        animationSpec = tween(
        durationMillis = 300,
        easing = LinearOutSlowInEasing
    ),
        targetValue = if (isExpanded) 0f else 1f
    )
    //---------- userDefinedColor is for the specific color choose by user from the colors database
    var userDefinedColor:String by remember{mutableStateOf("White")}
    var noteColorAsThemeState = if (isSystemInDarkTheme()) remember { mutableStateOf("Dark") } else remember { mutableStateOf("Light") }

    var noteColor =
        if (isSystemInDarkTheme()) remember { mutableStateOf(Color.Black.copy(alpha = 0.45f)) } else remember {
            mutableStateOf(Color.White)
        }
    var roundAsState = animateDpAsState(
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        ),
        targetValue = if (isExpanded) 0.dp else 30.dp
    )
    var plusColorAsState = animateColorAsState(
        animationSpec = tween(
            durationMillis = 400,
            easing = FastOutLinearInEasing
        ),
        targetValue = if (isExpanded) if (isSystemInDarkTheme()) Color.White else Color.Black else Color.White
    )
    var buttonRotationAsState = animateFloatAsState(
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearOutSlowInEasing
        ),
        targetValue = if (isExpanded) 225f else 0f
    )
    var buttonColorAsState = animateColorAsState(
        animationSpec = tween(
            delayMillis = 300,
            durationMillis = 1000,
            easing = FastOutLinearInEasing
        ),
        targetValue = if (isExpanded) Color.Unspecified else MaterialTheme.colors.primary
    )
    var (newNoteTitle,setNewNoteTitle) = remember { mutableStateOf("") }
    var (newNoteText,setNewNoteText) = remember { mutableStateOf("") }
    //This Composable is StateLess
    ButtonScreen(
        roundAsState,
        alignment,
        buttonColorAsState,
        isExpanded,
        onExpandedChange = {
                           isExpanded = it
        },
        noteColor,
        newNoteTitle,
        setNewNoteTitle,
        newNoteText,
        setNewNoteText,
        noteColorAsThemeState,
        userDefinedColor,
        onUserDefinedColorChange = {
            userDefinedColor = it
        },
        buttonRotationAsState,
        plusColorAsState,
        onAddTodoItem
    )
}
// this composable is StateLess
@Composable
fun ButtonScreen(
    roundAsState: State<Dp>,
    alignment: State<Float>,
    buttonColorAsState: State<Color>,
    isExpanded: Boolean,
    onExpandedChange:(Boolean) -> Unit,
    noteColor: MutableState<Color>,
    newNoteTitle: String,
    setNewNoteTitle: (String) -> Unit,
    newNoteText: String,
    setNewNoteText: (String) -> Unit,
    noteColorAsThemeState: MutableState<String>,
    userDefinedColor: String,
    onUserDefinedColorChange:(String)->Unit,
    buttonRotationAsState: State<Float>,
    plusColorAsState: State<Color>,
    onAddTodoItem:(TodoNote) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(roundAsState.value)
            .background(color = Color.Transparent),
        contentAlignment = BiasAlignment(alignment.value, alignment.value)
    ) {
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
                        delayMillis = 300,
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
            ) {
                if (isExpanded) {
                    Surface(
                        color = noteColor.value,
                        modifier = Modifier
                            .fillMaxSize(),
                        shape = MaterialTheme.shapes.large,
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            //----------------- Note/To-do title
                            NewTodoTextField(
                                text = newNoteTitle,
                                onTextChange = setNewNoteTitle,
                                placeHolder = "Enter Title",
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            // ----------------------- This text field is for Note/To-do description
                            NewTodoTextField(
                                text = newNoteText,
                                onTextChange = setNewNoteText,
                                placeHolder = "Enter Text",
                                maxLines = 4,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(300.dp, 500.dp)
                            )
                            //------------------ This part of code is used to display Color Buttons in the bottom floating button menu
                            //------------------ Used to Trigger note colors
                            ColorButtonsRow(noteColor, noteColorAsThemeState) {
                                onUserDefinedColorChange(it)
                            }
                            //------------ Save button to load the data to database
                            Button(
                                onClick = {
                                    onAddTodoItem(
                                        TodoNote(
                                            newNoteTitle,
                                            newNoteText,
                                            ColorsThemeStateList.itemsList.get("Light ${userDefinedColor}")!!,
                                            ColorsThemeStateList.itemsList.get("Dark ${userDefinedColor}")!!
                                        )
                                    )
                                    onExpandedChange(!isExpanded)
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
                    onClick = {
                        onExpandedChange(!isExpanded)
                        setNewNoteText("")
                        setNewNoteTitle("")
                    },
                    modifier = Modifier.rotate(buttonRotationAsState.value)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = "Add Todo",
                        tint = plusColorAsState.value
                    )
                }
            }
        }
    }
}

@Composable
fun NewTodoTextField(
    text:String,
    onTextChange:(String) -> Unit,
    placeHolder:String,
    maxLines:Int,
    modifier:Modifier
){
    TextField(
        value = text, onValueChange = { onTextChange(it) },
        placeholder = {
            Text(placeHolder)
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(8.dp),
        maxLines = maxLines,
        modifier = modifier
    )
}
@Composable
fun ColorButtonsRow(
    noteColor: MutableState<Color>,
    noteColorAsThemeState: MutableState<String>,
    onColorChange:(String)->Unit
){
    Row(
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RoundedColorButton(
            onClick = {
                noteColor.value =
                    ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} White")!!
                onColorChange("White")
            },
            color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} White")!!
        )
        RoundedColorButton(
            onClick = {
                noteColor.value =
                    ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Yellow")!!
                onColorChange("Yellow")
            },
            color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Yellow")!!,
            contentDescription = "Yellow Color"
        )
        RoundedColorButton(
            onClick = {
                noteColor.value =
                    ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Green")!!
                onColorChange("Green")
            },
            color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Green")!!,
            contentDescription = "Green Color"
        )
        RoundedColorButton(
            onClick = {
                noteColor.value =
                    ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Red")!!
                onColorChange("Red")
            },
            color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Red")!!,
            contentDescription = "Red Color"
        )
        RoundedColorButton(
            onClick = {
                noteColor.value =
                    ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Blue")!!
                onColorChange("Blue")
            },
            color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Blue")!!,
            contentDescription = "Blue Color"
        )
        RoundedColorButton(
            onClick = {
                noteColor.value =
                    ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Magenta")!!
                onColorChange("Magenta")
            },
            color = ColorsThemeStateList.itemsList.get("${noteColorAsThemeState.value} Magenta")!!,
            contentDescription = "Magenta Color"
        )
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
            modifier = Modifier
                .border(
                    border = borderThickness,
                    shape = CircleShape
                )
                .size(35.dp)
        )
    }
}
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true
)
@Composable
fun previewFloatingButton() {
    TodoTheme(){
        Surface(color = MaterialTheme.colors.surface){
            CustomFloatingButton(
//                TodoItems = listOf<TodoNote>(TodoNote("Sample Note","This is a sample Note",Color.Yellow,Color.Gray)),
                onAddTodoItem = {}
            )
        }
    }
}