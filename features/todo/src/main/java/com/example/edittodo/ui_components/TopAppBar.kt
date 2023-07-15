package com.example.edittodo.ui_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
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
import com.example.ui_core.Blue

@Preview
@Composable
fun TopAppBar(
    onSave: () -> Unit = {},
    onGoBack: () -> Unit = {}
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        IconButton(
            onClick = onGoBack,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Image(
                painter = painterResource(com.example.ui_core.R.drawable.cancel),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.save),
            modifier = Modifier
                .clickable { onSave() }
                .align(Alignment.CenterVertically),
            color = Blue,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}