package com.example.todolist.data.remote

import kotlinx.coroutines.delay
import retrofit2.Response

sealed interface NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>
    data class Error(val code: Int) : NetworkResult<Nothing>
}

fun <T, S> NetworkResult<T>.mapSuccess(
    transformer: (T) -> S
) = if (this is NetworkResult.Success) {
    NetworkResult.Success(transformer(this.data))
} else {
    NetworkResult.Error((this as NetworkResult.Error).code)
}

suspend fun <T> repeatOnError(
    countRepeat: Int,
    repeatDelay: Long,
    action: suspend () -> NetworkResult<T>
): NetworkResult<T> {
    if (countRepeat <= 0) return NetworkResult.Error(-1)
    var lastResult: NetworkResult<T>? = null
    for (i in 1..countRepeat) {
        lastResult = action()
        if (lastResult is NetworkResult.Success) break
        delay(repeatDelay)
    }
    return lastResult!!
}

fun <T> NetworkResult<T>.toResult(errorMessage: String) = if (this is NetworkResult.Success) {
    Result.success(this.data)
} else {
    Result.failure(IllegalStateException(errorMessage))
}

suspend fun <T> NetworkResult<T>.onSuccess(action: suspend (data: T) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Success) action(data)
    return this
}

suspend fun <T> NetworkResult<T>.onError(action: suspend (Int) -> Unit): NetworkResult<T> {
    if (this is NetworkResult.Error) action(this.code)
    return this
}

suspend fun <T> doNetworkAction(action: suspend () -> Response<T>): NetworkResult<T> {
    val response = action()
    return if (response.isSuccessful) {
        NetworkResult.Success(response.body()!!)
    } else {
        NetworkResult.Error(response.code())
    }
}