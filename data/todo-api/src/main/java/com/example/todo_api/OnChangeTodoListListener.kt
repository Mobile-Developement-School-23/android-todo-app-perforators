package com.example.todo_api

import com.example.todo_api.models.Items

interface OnChangeTodoListListener {

    fun onChange(newList: Items)
}