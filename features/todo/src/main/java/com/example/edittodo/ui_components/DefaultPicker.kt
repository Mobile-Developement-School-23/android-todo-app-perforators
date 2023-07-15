package com.example.edittodo.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui_core.Blue
import com.example.ui_core.ExtendedTheme
import com.example.ui_core.TodoAppTheme

@Composable
fun DefaultPicker(
    title: String = stringResource(R.string.deadline),
    text: String? = null,
    pickImage: Painter = painterResource(R.drawable.calendar),
    onPick: () -> Unit = {}
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = title,
                color = ExtendedTheme.colors.labelPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            if (text != null) {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    color = Blue
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = onPick,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Image(
                painter = pickImage,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun DefaultPickerPreview() {
    TodoAppTheme {
        Box(
            modifier = Modifier
                .background(ExtendedTheme.colors.backPrimary)
                .padding(16.dp)
        ) {
            DefaultPicker()
        }
    }
}