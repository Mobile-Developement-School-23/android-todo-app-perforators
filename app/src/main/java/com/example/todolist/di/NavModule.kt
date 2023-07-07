package com.example.todolist.di

import com.example.authorization.navigation.AuthorizationNavCommandProvider
import com.example.navigation.NavCommand
import com.example.todolist.navigation.TodoListNavCommandProvider
import dagger.Module
import dagger.Provides

@Module
class NavModule {

    @Provides
    fun provideAuthorizationNavProvider(): AuthorizationNavCommandProvider {
        return object : AuthorizationNavCommandProvider {
            override fun navigateToTodoList(): NavCommand {
                return NavCommand(com.example.app.R.id.action_authorizationFragment_to_todoListFragment)
            }
        }
    }

    @Provides
    fun provideTodoListNavProvider(): TodoListNavCommandProvider {
        return object : TodoListNavCommandProvider {
            override fun navigateToEditScreen(): NavCommand {
                return NavCommand(com.example.app.R.id.action_todoListFragment_to_detailFragment)
            }
        }
    }
}