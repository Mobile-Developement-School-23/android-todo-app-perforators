package com.example.todo.workers

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkersPipeline @Inject constructor(
    private val workers: Set<@JvmSuppressWildcards Worker>
) {

    fun init() {
        workers.forEach {
            it.doWork()
        }
    }
}