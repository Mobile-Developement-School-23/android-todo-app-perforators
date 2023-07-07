package com.example.todolist

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.todolist.data.worker.SynchronizedWorker
import com.example.todolist.data.worker.SynchronizedWorkerFactory
import com.example.todolist.di.application.ApplicationComponent
import com.example.todolist.di.application.DaggerApplicationComponent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var workerFactory: SynchronizedWorkerFactory

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
        applicationComponent.inject(this)
        configureWorkManager()
        configurePeriodicSynchronization()
    }

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(this, config)
    }

    private fun configurePeriodicSynchronization() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            SynchronizedWorker::class.java, REPEAT_INTERVAL, TimeUnit.HOURS
        ).setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        ).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            TAG, ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE, periodicWorkRequest
        )
    }

    companion object {
        private const val TAG = "worker"
        private const val REPEAT_INTERVAL = 8L
    }
}

fun Fragment.appComponent() = (requireActivity().application as App).applicationComponent