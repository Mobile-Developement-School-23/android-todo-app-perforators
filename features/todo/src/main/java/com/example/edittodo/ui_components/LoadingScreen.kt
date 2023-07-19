package com.example.edittodo.ui_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui_core.ExtendedTheme
import com.example.ui_core.TodoAppTheme

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ExtendedTheme.colors.backPrimary),
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = ExtendedTheme.colors.labelPrimary
        )
    }
}

@Preview
@Composable
private fun LoadingScreenPreview() {
    TodoAppTheme {
        LoadingScreen()
    }
}