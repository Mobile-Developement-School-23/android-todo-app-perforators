package com.example.todolist

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.commom.convertToString
import com.example.domain.models.Importance
import com.example.domain.models.TodoItem

class TodoItemViewHolder(
    private val root: View,
    private val onItemClick: (TodoItem) -> Unit,
    private val onToggleClick: (TodoItem) -> Unit
): RecyclerView.ViewHolder(root) {

    private val isDone: CheckBox = root.findViewById(R.id.is_done)
    private val text: TextView = root.findViewById(R.id.text)
    private val deadline: TextView = root.findViewById(R.id.deadline)
    private val importance: ImageView = root.findViewById(R.id.importance)

    fun bind(item: TodoItem) {
        setupListeners(item)
        setupImportance(item)
        setupText(item)
        isDone.isChecked = item.isDone
        deadline.isVisible = item.deadline != null
        if (item.deadline != null) deadline.text = item.deadline!!.convertToString()
    }

    private fun setupListeners(item: TodoItem) {
        root.setOnClickListener {
            onItemClick(item)
        }
        isDone.setOnClickListener {
            onToggleClick(item)
        }
    }

    private fun setupImportance(item: TodoItem) {
        importance.isVisible = item.importance != Importance.NORMAL
        when (item.importance) {
            Importance.LOW -> {
                importance.setImageResource(R.drawable.low)
                isDone.buttonTintList = root.context.getColorStateList(R.color.checkbox_selector)
            }
            Importance.HIGH -> {
                importance.setImageResource(R.drawable.urgent)
                isDone.buttonTintList = root.context.getColorStateList(R.color.checkbox_selector_red)
            }
            Importance.NORMAL -> {
                isDone.buttonTintList = root.context.getColorStateList(R.color.checkbox_selector)
            }
        }
    }

    private fun setupText(item: TodoItem) {
        text.text = item.text
        if (item.isDone) {
            text.paintFlags = text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            text.setTextColor(root.context.getColor(com.example.ui_core.R.color.label_tertiary))
        } else {
            text.paintFlags = text.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            text.setTextColor(root.context.getColor(com.example.ui_core.R.color.label_primary))
        }
    }
}