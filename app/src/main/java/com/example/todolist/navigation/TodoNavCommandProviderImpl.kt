package com.example.todolist.navigation

import android.app.PendingIntent
import android.content.Context
import androidx.core.os.bundleOf
import androidx.navigation.NavDeepLinkBuilder
import com.example.app.R
import com.example.edittodo.DetailFragment.Companion.ITEM_KEY
import com.example.navigation.NavCommand
import com.example.navigation.TodoNavCommandProvider
import javax.inject.Inject

class TodoNavCommandProviderImpl @Inject constructor(
    private val context: Context
) : TodoNavCommandProvider {

    override fun navigateToSettings(): NavCommand {
        return NavCommand(R.id.action_todoListFragment_to_settingsFragment)
    }

    override fun navigateToEditTodo(): NavCommand {
        return NavCommand(R.id.action_todoListFragment_to_detailFragment)
    }

    override fun deepLinkToEditTodo(itemId: String) = NavDeepLinkBuilder(context)
        .setGraph(R.navigation.navigation)
        .setDestination(R.id.detailFragment)
        .setArguments(bundleOf(ITEM_KEY to itemId))
        .createPendingIntent()
}

