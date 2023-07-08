package com.example.data.workers.network

import com.example.data.workers.network.connectivityobserver.ConnectivityObserver
import com.example.data.workers.Worker
import com.example.domain.TodoItemsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Singleton
class NetworkAvailableWorker(
    private val connectivityObserver: ConnectivityObserver,
    private val repository: TodoItemsRepository,
    dispatcher: CoroutineDispatcher
) : Worker {

    private val scope = CoroutineScope(dispatcher)

    override fun doWork() {
        scope.launch {
            connectivityObserver.observe().collect {
                if (it == ConnectivityObserver.Status.Available) repository.fetchAll()
            }
        }
    }
}