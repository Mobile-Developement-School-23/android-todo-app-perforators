package com.example.edittodo.ui_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.commom.splitOnTimeAndDate
import com.example.edittodo.DetailViewModel
import com.example.todo_api.models.Importance
import com.example.ui_core.ExtendedTheme
import com.example.ui_core.TodoAppTheme

@Composable
fun ContentScreen(
    onGoBack: () -> Unit = {},
    onSave: () -> Unit = {},
    onDelete: () -> Unit = {},
    onSelect: (DetailViewModel.SelectAction) -> Unit = {},
    onShowBottomSheet: () -> Unit = {},
    importance: Importance = Importance.NORMAL,
    state: DetailViewModel.ScreenState = DetailViewModel.ScreenState()
) {
    Scaffold(
        topBar = { TopAppBar(onSave, onGoBack) },
        containerColor = ExtendedTheme.colors.backPrimary
    ) {
        val (date, time) = state.item.deadline?.splitOnTimeAndDate() ?: (null to null)
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            TodoTextField(state.item.text, onSelect)
            PickImportance(importance, onShowBottomSheet)
            DefaultDivider()
            PickDateDeadline(date, onSelect)
            DefaultDivider()
            PickTimeDeadline(time, onSelect)
            DefaultDivider()
            DeleteLayout(state.isNew, onDelete)
        }
    }
}

@Preview
@Composable
private fun ContentScreenPreview() {
    TodoAppTheme {
        ContentScreen()
    }
}