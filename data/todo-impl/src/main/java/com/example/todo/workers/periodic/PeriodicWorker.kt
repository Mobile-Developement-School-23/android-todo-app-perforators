package com.example.todo.workers.periodic

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todo.workers.Worker
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class PeriodicWorker(
    private val context: Context
) : Worker {

    override fun doWork() {
        val request = createRequest()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            TAG, ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, request
        )
    }

    private fun createRequest() = PeriodicWorkRequest.Builder(
        SynchronizedWorker::class.java, REPEAT_INTERVAL, TimeUnit.HOURS
    ).setConstraints(
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    ).build()

    companion object {
        private const val TAG = "worker"
        private const val REPEAT_INTERVAL = 8L
    }
}