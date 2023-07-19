package com.example.notification

import com.example.todo_api.OnChangeTodoListListener
import com.example.todo_api.models.Items
import javax.inject.Inject

class ChangeListener @Inject constructor(
    private val deferredNotifications: DeferredNotifications
) : OnChangeTodoListListener {

    override fun onChange(newList: Items) {
        newList.items
            .filter { it.deadline != null }
            .filter { it.deadline!!.time >= System.currentTimeMillis() }
            .forEach { deferredNotifications.registerFor(it) }
    }
}