package com.example.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.di.DaggerTodoFeatureComponent
import com.example.di.TodoFeatureDepsStore
import com.example.todo_api.TodoItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class DelayNotificationReceiver : BroadcastReceiver() {

    @Inject
    internal lateinit var repository: TodoItemsRepository

    @Inject
    internal lateinit var dispatcher: CoroutineDispatcher

    private val scope by lazy { CoroutineScope(dispatcher) }

    override fun onReceive(context: Context, intent: Intent?) {
        setupDi()
        val itemId = intent?.getStringExtra(ITEM_ID) ?: return
        hideNotification(itemId, context)
        scope.launch {
            repository.incDeadline(itemId, MILLIS_IN_DAY)
        }
    }

    private fun setupDi() {
        DaggerTodoFeatureComponent.factory()
            .create(TodoFeatureDepsStore.deps)
            .inject(this)
    }

    private fun hideNotification(itemId: String, context: Context) {
        val notificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(itemId.hashCode())
    }

    companion object {
        private const val ITEM_ID = "item_id"
        private const val MILLIS_IN_DAY = 60L * 60 * 24 * 1000

        fun newIntent(
            context: Context,
            itemId: String
        ) = Intent(context, DelayNotificationReceiver::class.java).apply {
            putExtra(ITEM_ID, itemId)
        }
    }
}