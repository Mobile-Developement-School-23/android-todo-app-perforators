package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.R
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_api.models.TodoItem

class TodoListAdapter(
    private val onItemClick: (TodoItem) -> Unit,
    private val onToggleClick: (TodoItem) -> Unit
) : RecyclerView.Adapter<TodoItemViewHolder>() {

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