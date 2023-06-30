package com.example.todolist.data

import com.example.todolist.domain.TodoItemsRepository
import com.example.todolist.domain.models.Importance
import com.example.todolist.domain.models.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Locale

class TodoItemsRepositoryImpl : TodoItemsRepository {

    private val items = MutableStateFlow(generateItems())

    private fun generateItems(): List<TodoItem> {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale("ru"))
        return listOf(
            TodoItem("0", "Дело №1", Importance.LOW, false, formatter.parse("20/06/2023")!!,
                deadline = formatter.parse("20/06/2023")!!),
            TodoItem("1", "Дело №2\nДа\nЛадк\nfdfa", Importance.NORMAL, true, formatter.parse("24/06/2023")!!),
            TodoItem("2", "Дело №3", Importance.HIGH, true, formatter.parse("20/06/2023")!!),
            TodoItem("3", "Дело №4", Importance.LOW, true, formatter.parse("27/07/2023")!!,
                deadline = formatter.parse("23/06/2023")!!),
            TodoItem("4", "Дело №5", Importance.NORMAL, false, formatter.parse("20/08/2023")!!,
                changeData = formatter.parse("05/06/2023")!!),
            TodoItem("5", "Дело №6", Importance.LOW, false, formatter.parse("20/09/2023")!!),
            TodoItem("6", "Дело №7", Importance.HIGH, true, formatter.parse("29/10/2023")!!),
            TodoItem("7", "Дело №8", Importance.NORMAL, false, formatter.parse("20/06/2023")!!),
            TodoItem("8", "Дело №9", Importance.HIGH, true, formatter.parse("20/06/2023")!!),
            TodoItem("9", "Дело №10", Importance.LOW, false, formatter.parse("23/06/2023")!!),
            TodoItem("10", "Дело №11", Importance.NORMAL, true, formatter.parse("25/06/2023")!!),
            TodoItem("11", "Дело №12", Importance.HIGH, false, formatter.parse("20/06/2023")!!),
            TodoItem("12", "Дело №13", Importance.LOW, false, formatter.parse("01/07/2023")!!),
            TodoItem("13", "Дело №14", Importance.NORMAL, false, formatter.parse("20/06/2023")!!),
            TodoItem("14", "Дело №15", Importance.LOW, false, formatter.parse("20/06/2023")!!),
        )
    }

    override fun fetchAll(): Flow<List<TodoItem>> = items.map { currentItems ->
        currentItems.sortedBy { item -> item.id }
    }

    override fun fetchOne(id: String): Result<TodoItem> {
        return runCatching {
            items.value.find { item -> item.id == id } ?: error("No item with id $id")
        }
    }

    override fun save(item: TodoItem) {
        items.update { oldItems ->
            oldItems.filter { it.id != item.id } + item
        }
    }

    override fun remove(item: TodoItem) {
        items.update { oldItems ->
            oldItems.toMutableList().apply { remove(item) }
        }
    }
}