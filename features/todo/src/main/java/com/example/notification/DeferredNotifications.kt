package com.example.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.AlarmManagerCompat
import com.example.todo_api.models.TodoItem
import javax.inject.Inject

class DeferredNotifications @Inject constructor(
    private val context: Context
) {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun registerFor(item: TodoItem) {
        val notifyIntent = NotifyReceiver.newIntent(context, item)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            item.id.hashCode(),
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            item.deadline!!.time,
            pendingIntent
        )
    }
}