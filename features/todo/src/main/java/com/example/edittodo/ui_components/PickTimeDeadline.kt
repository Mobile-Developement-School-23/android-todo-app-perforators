package com.example.edittodo.ui_components

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.commom.convertToString
import com.example.edittodo.DetailViewModel
import com.example.ui_core.ExtendedTheme
import com.example.ui_core.TodoAppTheme
import java.util.Calendar

@Composable
fun PickTimeDeadline(
    startTime: String? = null,
    onSelect: (DetailViewModel.SelectAction.SelectTimeDeadline) -> Unit = {}
) {
    var time by rememberSaveable { mutableStateOf(startTime) }
    val context = LocalContext.current
    DefaultPicker(
        title = stringResource(R.string.deadline_time),
        text = time,
        pickImage = painterResource(R.drawable.watch),
        onPick = {
            showTimePicker(context) {
                time = it
                onSelect(DetailViewModel.SelectAction.SelectTimeDeadline(it))
            }
        }
    )
}


fun showTimePicker(
    context: Context,
    onPick: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val hour: Int = calendar.get(Calendar.HOUR_OF_DAY)
    val minute: Int = calendar.get(Calendar.MINUTE)
    TimePickerDialog(
        context,
        { _, currentHour, currentMinute ->
            onPick(convertToString(currentHour, currentMinute))
        }, hour, minute, true
    ).show()
}


@Preview
@Composable
private fun PickTimeDeadlinePreview() {
    TodoAppTheme {
        Box(
            modifier = Modifier
                .background(ExtendedTheme.colors.backPrimary)
                .padding(16.dp)
        ) {
            PickTimeDeadline()
        }
    }
}