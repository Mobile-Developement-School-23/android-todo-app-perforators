package com.example.edittodo.ui_components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.edittodo.DetailViewModel
import com.example.todo_api.models.Importance
import com.example.todo_api.models.importanceFrom
import com.example.ui_core.ExtendedTheme
import com.example.ui_core.Red_50
import com.example.ui_core.TodoAppTheme
import kotlinx.coroutines.launch

private const val ANIMATION_DURATION = 200

@Composable
fun SheetContent(
    onSelect: (DetailViewModel.SelectAction.SelectImportance) -> Unit = {}
) {
    LazyColumn {
        items(Importance.values().size) {
            Item(it, onSelect)
        }
    }
}

@Composable
fun Item(
    num: Int = 0,
    onSelect: (DetailViewModel.SelectAction.SelectImportance) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val defaultColor = ExtendedTheme.colors.backSecondary
    val background = remember { Animatable(defaultColor) }
    val importance = importanceFrom(num)
    Text(
        text = importance.value,
        color = ExtendedTheme.colors.labelPrimary,
        modifier = Modifier
            .padding(16.dp)
            .background(background.value)
            .clickable {
                scope.launch {
                    if (importance == Importance.HIGH) {
                        background.animateTo(Red_50, tween(ANIMATION_DURATION))
                        background.animateTo(defaultColor, tween(ANIMATION_DURATION))
                    }
                    onSelect(DetailViewModel.SelectAction.SelectImportance(importance))
                }
            }
    )
}

@Preview
@Composable
private fun SheetContentPreview() {
    TodoAppTheme {
        Column(
            modifier = Modifier.background(ExtendedTheme.colors.backSecondary)
        ) {
            SheetContent()
        }
    }
}

@Preview
@Composable
private fun ItemPreview() {
    TodoAppTheme {
        Box(
            modifier = Modifier.background(ExtendedTheme.colors.backSecondary)
        ) {
            Item()
        }
    }
}