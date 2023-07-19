package com.example.todolist

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.authorization.di.AuthorizationFeatureDepsStore
import com.example.di.TodoFeatureDepsStore
import com.example.settings.di.SettingsFeatureDepsStore
import com.example.todo.workers.WorkersPipeline
import com.example.todo.workers.periodic.SynchronizedWorkerFactory
import com.example.todolist.di.ApplicationComponent
import com.example.todolist.di.DaggerApplicationComponent
import javax.inject.Inject

class App : Application() {

    internal lateinit var applicationComponent: ApplicationComponent

    @Inject
    internal lateinit var workerFactory: SynchronizedWorkerFactory

    @Inject
    internal lateinit var workersPipeline: WorkersPipeline

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
        applicationComponent.inject(this)
        initDependencies()
        configureWorkManager()
        workersPipeline.init()
    }

    private fun initDependencies() {
        AuthorizationFeatureDepsStore.deps = applicationComponent
        TodoFeatureDepsStore.deps = applicationComponent
        SettingsFeatureDepsStore.deps = applicationComponent
    }

    private fun configureWorkManager() {
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
        WorkManager.initialize(this, config)
    }
}
