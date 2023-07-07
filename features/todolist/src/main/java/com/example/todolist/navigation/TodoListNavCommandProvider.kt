package com.example.todolist.navigation

import com.example.navigation.NavCommand

interface TodoListNavCommandProvider {

    fun navigateToEditScreen(): NavCommand
}