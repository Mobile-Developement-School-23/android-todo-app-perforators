package com.example.todolist.navigation

import com.example.app.R
import com.example.authorization.navigation.AuthorizationNavCommandProvider
import com.example.navigation.NavCommand
import javax.inject.Inject

class AuthorizationNavCommandProviderImpl @Inject constructor() : AuthorizationNavCommandProvider {

    override fun navigateToTodoList(): NavCommand {
        return NavCommand(R.id.action_authorizationFragment_to_todoListFragment)
    }
}
