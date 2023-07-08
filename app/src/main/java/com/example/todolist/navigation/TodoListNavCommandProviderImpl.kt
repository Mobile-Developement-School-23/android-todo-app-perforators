package com.example.todolist.navigation

import com.example.app.R
import com.example.navigation.NavCommand
import javax.inject.Inject

class TodoListNavCommandProviderImpl @Inject constructor() : TodoListNavCommandProvider {

    override fun navigateToEditScreen(): NavCommand {
        return NavCommand(R.id.action_todoListFragment_to_detailFragment)
    }
}

