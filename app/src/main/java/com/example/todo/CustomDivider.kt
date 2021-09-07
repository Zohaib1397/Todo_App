package com.example.todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider(
    desiredState: String
) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
        Text(
            text = desiredState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f))
                .padding(start = 30.dp,top = 8.dp,bottom = 8.dp)
        )
    }
}