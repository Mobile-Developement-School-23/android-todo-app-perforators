package com.example.edittodo.ui_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.R
import com.example.todo_api.models.Importance

@Preview
@Composable
fun PickImportance(
    importance: Importance = Importance.NORMAL,
    onShowBottomSheet: () -> Unit = {}
) {
    DefaultPicker(
        title = stringResource(R.string.importance),
        text = importance.value,
        pickImage = painterResource(R.drawable.pick),
        onPick = onShowBottomSheet
    )
}