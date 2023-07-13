package com.example.todolist.navigation

import com.example.app.R
import com.example.navigation.NavCommand
import javax.inject.Inject

class TodoNavCommandProviderImpl @Inject constructor() : TodoNavCommandProvider {

    override fun navigateToSettings(): NavCommand {
        return NavCommand(R.id.action_todoListFragment_to_settingsFragment)
    }

    override fun navigateToEditTodo(): NavCommand {
        return NavCommand(R.id.action_todoListFragment_to_detailFragment)
    }
}

