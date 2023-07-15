package com.example.edittodo.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui_core.ExtendedTheme
import com.example.ui_core.Red

@Preview
@Composable
fun DeleteLayout(
    isNewItem: Boolean = false,
    onDelete: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable(enabled = isNewItem.not()) { onDelete() }
            .padding(16.dp)
    ) {
        val icon = if (isNewItem) R.drawable.delete_disable else R.drawable.delete
        Image(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            painter = painterResource(icon),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .align(Alignment.CenterVertically),
            text = stringResource(R.string.delete),
            fontSize = 14.sp,
            color = if (isNewItem) ExtendedTheme.colors.labelDisable else Red,
            fontWeight = FontWeight.Bold
        )
    }
}