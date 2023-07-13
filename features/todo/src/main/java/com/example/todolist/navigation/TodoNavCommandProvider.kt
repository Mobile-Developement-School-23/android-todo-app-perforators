package com.example.todolist.navigation

import com.example.navigation.NavCommand

interface TodoNavCommandProvider {

    fun navigateToSettings(): NavCommand

    fun navigateToEditTodo(): NavCommand
}