package com.example.commom

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SafeHolder<T>(initializer: () -> T) {

    private var value: T = initializer()
    private val mutex = Mutex()

    suspend fun update(action: (T) -> T) = mutex.withLock {
        value = action(value)
    }

    suspend fun get() = mutex.withLock { value }
}