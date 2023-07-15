package com.example.edittodo.ui_components

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.R
import com.example.commom.convertToString
import com.example.edittodo.DetailViewModel
import java.util.Calendar

@Preview
@Composable
fun PickDateDeadline(
    startDeadline: String? = null,
    onSelect: (DetailViewModel.SelectAction.SelectDateDeadline) -> Unit = {}
) {
    val context = LocalContext.current
    var date by rememberSaveable {
        mutableStateOf(startDeadline)
    }
    DefaultPicker(
        title = stringResource(R.string.deadline),
        text = date,
        pickImage = painterResource(R.drawable.calendar),
        onPick = {
            showDatePicker(context) {
                date = it
                onSelect(DetailViewModel.SelectAction.SelectDateDeadline(it))
            }
        }
    )
}

fun showDatePicker(
    context: Context,
    onPick: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    val month: Int = calendar.get(Calendar.MONTH)
    val year: Int = calendar.get(Calendar.YEAR)
    DatePickerDialog(
        context,
        { _, currentYear, monthOfYear, dayOfMonth ->
            onPick(convertToString(dayOfMonth, monthOfYear, currentYear))
        }, year, month, day
    ).show()
}
