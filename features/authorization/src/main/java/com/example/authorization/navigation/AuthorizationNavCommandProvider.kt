package com.example.authorization.navigation

import com.example.navigation.NavCommand

interface AuthorizationNavCommandProvider {

    fun navigateToTodoList(): NavCommand
}