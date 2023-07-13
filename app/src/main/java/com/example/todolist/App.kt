package com.example.todolist

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import com.example.authorization.di.AuthorizationDepsStore
import com.example.todo.workers.WorkersPipeline
import com.example.todo.workers.periodic.SynchronizedWorkerFactory
import com.example.edittodo.di.DetailDepsStore
import com.example.todolist.di.ApplicationComponent
import com.example.todolist.di.DaggerApplicationComponent
import com.example.todolist.di.TodoItemsDepsStore
import javax.inject.Inject

class App : Application() {

    private lateinit var applicationComponent: ApplicationComponent

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
}
