package com.example.todolist

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.authorization.di.AuthorizationDepsStore
import com.example.data.worker.SynchronizedWorkerFactory
import com.example.edittodo.di.DetailDepsStore
import com.example.todolist.di.ApplicationComponent
import com.example.todolist.di.DaggerApplicationComponent
import com.example.todolist.di.TodoItemsDepsStore
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var workerFactory: SynchronizedWorkerFactory

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
        initDependencies()
        applicationComponent.inject(this)
        configureWorkManager()
        configurePeriodicSynchronization()
    }

    private fun initDependencies() {
        AuthorizationDepsStore.deps = applicationComponent
        DetailDepsStore.deps = applicationComponent
        TodoItemsDepsStore.deps = applicationComponent
    }

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(this, config)
    }

    private fun configurePeriodicSynchronization() {
        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            com.example.data.worker.SynchronizedWorker::class.java, REPEAT_INTERVAL, TimeUnit.HOURS
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