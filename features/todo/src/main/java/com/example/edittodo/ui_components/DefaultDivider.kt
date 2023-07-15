package com.example.edittodo.ui_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui_core.ExtendedTheme

@Preview
@Composable
fun DefaultDivider() {
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 32.dp, start = 16.dp, end = 16.dp),
        color = ExtendedTheme.colors.supportSeparator
    )
}