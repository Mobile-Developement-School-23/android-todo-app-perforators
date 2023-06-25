package com.example.todolist.screens.todoitems

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todolist.R
import com.example.todolist.models.Importance
import com.example.todolist.models.TodoItem
import com.example.todolist.util.convertToString

class TodoListAdapter(
    private val onItemClick: (TodoItem) -> Unit,
    private val onToggleClick: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.TodoItemViewHolder>() {

    private val differ: AsyncListDiffer<TodoItem> = AsyncListDiffer(this, DiffCallback())

    class DiffCallback : DiffUtil.ItemCallback<TodoItem>() {

        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem) =
            oldItem == newItem
    }

    fun submitItems(items: List<TodoItem>) {
        differ.submitList(items)
    }

    class TodoItemViewHolder(
        private val root: View,
        private val onItemClick: (TodoItem) -> Unit,
        private val onToggleClick: (TodoItem) -> Unit
    ): ViewHolder(root) {

        private val isDone: CheckBox = root.findViewById(R.id.is_done)
        private val text: TextView = root.findViewById(R.id.text)
        private val deadline: TextView = root.findViewById(R.id.deadline)
        private val importance: ImageView = root.findViewById(R.id.importance)

        fun bind(item: TodoItem) {
            root.setOnClickListener {
                onItemClick(item)
            }
            isDone.setOnClickListener {
                onToggleClick(item)
            }
            isDone.isChecked = item.isDone
            text.text = item.text
            if (item.isDone) {
                text.paintFlags = text.paintFlags or STRIKE_THRU_TEXT_FLAG
                text.setTextColor(root.context.getColor(R.color.label_tertiary))
            } else {
                text.paintFlags = text.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
                text.setTextColor(root.context.getColor(R.color.label_primary))
            }
            deadline.isVisible = item.deadline != null
            if (item.deadline != null) deadline.text = item.deadline.convertToString()
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TodoItemViewHolder(
            layoutInflater.inflate(R.layout.item_layout, parent, false),
            onItemClick,
            onToggleClick
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: TodoItemViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}