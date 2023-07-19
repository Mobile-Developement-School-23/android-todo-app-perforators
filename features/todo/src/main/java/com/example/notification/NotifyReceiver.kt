package com.example.notification

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.todo_api.TodoItemsRepository
import com.example.todo_api.models.TodoItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import com.example.R
import com.example.di.DaggerTodoFeatureComponent
import com.example.di.TodoFeatureDepsStore
import com.example.navigation.TodoNavCommandProvider
import com.example.settings_api.SettingsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotifyReceiver : BroadcastReceiver() {

    @Inject
    internal lateinit var repository: TodoItemsRepository

    @Inject
    internal lateinit var settingsRepository: SettingsRepository

    @Inject
    internal lateinit var dispatcher: CoroutineDispatcher

    @Inject
    internal lateinit var commandProvider: TodoNavCommandProvider

    private val scope by lazy { CoroutineScope(dispatcher) }

    override fun onReceive(context: Context, intent: Intent?) {
        initDi()
        val itemId = intent?.getStringExtra(ITEM_ID) ?: return
        scope.launch {
            repository.fetchOne(itemId).onSuccess {
                showNotification(it, context)
            }
        }
    }

    private fun initDi() {
        DaggerTodoFeatureComponent.factory()
            .create(TodoFeatureDepsStore.deps)
            .inject(this)
    }

    private suspend fun showNotification(item: TodoItem, context: Context) {
        if (!canShowNotification(context)) return
        val channelId = context.getString(R.string.channel_id)
        val remoteView = inflateNotificationView(context, item)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.add)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(remoteView)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        val notifyManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifyManager.notify(item.id.hashCode(), builder)
    }

    private fun inflateNotificationView(
        context: Context,
        item: TodoItem
    ): RemoteViews {
        return RemoteViews(context.packageName, R.layout.notification_layout).apply {
            setTextViewText(R.id.todo_name, item.text)
            setTextViewText(R.id.importance, item.importance.value)
            setOnClickPendingIntent(R.id.delay, createDelayButtonPendingIntent(context, item))
            setOnClickPendingIntent(R.id.content, commandProvider.deepLinkToEditTodo(item.id))
        }
    }

    private fun createDelayButtonPendingIntent(
        context: Context,
        item: TodoItem
    ): PendingIntent {
        return PendingIntent.getBroadcast(
            context.applicationContext,
            item.hashCode(),
            DelayNotificationReceiver.newIntent(context.applicationContext, itemId = item.id),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private suspend fun canShowNotification(context: Context): Boolean {
        if (settingsRepository.fetchSettings().showNotifications.not()) return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }

    companion object {

        private const val ITEM_ID = "item_id"

        fun newIntent(
            context: Context,
            item: TodoItem
        ) = Intent(context, NotifyReceiver::class.java).apply {
            putExtra(ITEM_ID, item.id)
        }
    }
}