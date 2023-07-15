package com.example.edittodo.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.edittodo.DetailViewModel
import com.example.todo_api.models.Importance
import com.example.todo_api.models.importanceFrom
import com.example.ui_core.ExtendedTheme
import com.example.ui_core.TodoAppTheme

@Composable
fun ColumnScope.SheetContent(
    onSelect: (DetailViewModel.SelectAction.SelectImportance) -> Unit = {}
) {
    LazyColumn {
        items(Importance.values().size) {
            val importance = importanceFrom(it)
            val commonModifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .clickable {
                    onSelect(DetailViewModel.SelectAction.SelectImportance(importance))
                }
            Text(
                text = importance.value,
                modifier = commonModifier
            )
        }
    }
}

@Preview
@Composable
private fun SheetContentPreview() {
    TodoAppTheme {
        Column(
            modifier = Modifier.background(ExtendedTheme.colors.backPrimary)
        ) {
            SheetContent()
        }
    }
}