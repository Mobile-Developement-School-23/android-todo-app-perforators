package com.example.edittodo.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.todo_api.models.Importance
import com.example.ui_core.ExtendedTheme
import com.example.ui_core.TodoAppTheme

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

@Preview
@Composable
private fun PickImportancePreview() {
    TodoAppTheme {
        Box(
            modifier = Modifier
                .background(ExtendedTheme.colors.backPrimary)
                .padding(16.dp)
        ) {
            PickImportance()
        }
    }
}