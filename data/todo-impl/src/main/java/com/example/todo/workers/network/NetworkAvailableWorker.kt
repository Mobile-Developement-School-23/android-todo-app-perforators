package com.example.todo.workers.network

import com.example.todo.workers.network.connectivityobserver.ConnectivityObserver
import com.example.todo.workers.Worker
import com.example.todo_api.TodoItemsRepository
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