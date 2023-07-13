package com.example.todo.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.todo_api.TodoItemsRepository

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