package com.example.edittodo.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.edittodo.DetailViewModel
import com.example.ui_core.ExtendedTheme
import com.example.ui_core.TodoAppTheme

@Composable
fun TodoTextField(
    startText: String = "",
    onSelect: (DetailViewModel.SelectAction.SelectText) -> Unit = {}
) {
    var text by rememberSaveable { mutableStateOf(startText) }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 32.dp),
        value = text,
        onValueChange = { newText ->
            text = newText
            onSelect(DetailViewModel.SelectAction.SelectText(newText))
        },
        textStyle = TextStyle(color = ExtendedTheme.colors.labelPrimary),
        colors = TextFieldDefaults.colors(
            cursorColor = ExtendedTheme.colors.labelPrimary,
        )
    )
}

@Preview
@Composable
private fun TodoTextFieldPreview() {
    TodoAppTheme {
        Box(
            modifier = Modifier
                .background(ExtendedTheme.colors.backPrimary)
                .padding(16.dp)
        ) {
            TodoTextField()
        }
    }
}
