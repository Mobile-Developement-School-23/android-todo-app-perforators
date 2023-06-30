package com.example.todolist.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedRequest = request.newBuilder()
            .header(AUTHORIZATION_HEADER_NAME, "Bearer $apiKey")
            .build()
        return chain.proceed(updatedRequest)
    }

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }
}