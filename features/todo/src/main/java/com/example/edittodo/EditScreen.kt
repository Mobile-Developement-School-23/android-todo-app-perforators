package com.example.edittodo

import android.widget.Toast
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.edittodo.ui_components.ContentScreen
import com.example.edittodo.ui_components.LoadingScreen
import com.example.edittodo.ui_components.SheetContent
import com.example.ui_core.TodoAppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditScreen(
    onGoBack: () -> Unit = {},
    onSave: () -> Unit = {},
    onDelete: () -> Unit = {},
    onSelect: (DetailViewModel.SelectAction) -> Unit = {},
    state: DetailViewModel.ScreenState = DetailViewModel.ScreenState(),
    events: Flow<DetailViewModel.Event> = flowOf()
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                DetailViewModel.Event.GoBack -> onGoBack()
                is DetailViewModel.Event.ShowError ->
                    Toast.makeText(context, event.text, Toast.LENGTH_SHORT).show()
            }
        }
    }
    if (state.isLoading) {
        LoadingScreen()
    } else {
        val bottomSheetState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        val coroutineScope = rememberCoroutineScope()
        var selectedImportance by rememberSaveable { mutableStateOf(state.item.importance) }
        ModalBottomSheetLayout(
            sheetContent = {
                SheetContent {
                    onSelect(it)
                    selectedImportance = it.importance
                    coroutineScope.launch { bottomSheetState.hide() }
                }
            },
            sheetState = bottomSheetState
        ) {
            ContentScreen(
                onGoBack = onGoBack,
                onSave = onSave,
                onDelete = onDelete,
                onSelect = onSelect,
                onShowBottomSheet = { coroutineScope.launch { bottomSheetState.show() } },
                selectedImportance,
                state = state
            )
        }
    }
}

@Preview
@Composable
private fun PreviewEditScreenLight() {
    TodoAppTheme(darkTheme = false) {
        EditScreen()
    }
}

@Preview
@Composable
private fun PreviewEditScreenDark() {
    TodoAppTheme(darkTheme = true) {
        EditScreen()
    }
}