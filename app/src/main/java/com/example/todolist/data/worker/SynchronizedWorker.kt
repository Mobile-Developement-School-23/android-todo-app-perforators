package com.example.todolist.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todolist.domain.TodoItemsRepository

class SynchronizedWorker(
    appContext: Context,
    params: WorkerParameters,
    private val repository: TodoItemsRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        repository.fetchAll()
        return Result.success()
    }
}