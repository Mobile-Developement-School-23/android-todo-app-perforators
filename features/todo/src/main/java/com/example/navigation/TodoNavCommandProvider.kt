package com.example.navigation

import android.app.PendingIntent

interface TodoNavCommandProvider {

    fun navigateToSettings(): NavCommand

    fun navigateToEditTodo(): NavCommand

    fun deepLinkToEditTodo(itemId: String): PendingIntent
}