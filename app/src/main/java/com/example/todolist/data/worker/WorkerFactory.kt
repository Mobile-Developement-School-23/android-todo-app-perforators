package com.example.todolist.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.todolist.domain.TodoItemsRepository
import javax.inject.Inject

class SynchronizedWorkerFactory @Inject constructor(
    private val repository: TodoItemsRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return SynchronizedWorker(appContext, workerParameters, repository)
    }
}