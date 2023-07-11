package com.example.data.remote.api

import com.example.data.local.sources.TokenLocalDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedRequest = request.newBuilder()
            .header(AUTHORIZATION_HEADER_NAME, tokenLocalDataSource.token.value)
            .build()
        return chain.proceed(updatedRequest)
    }

    companion object {
        private const val AUTHORIZATION_HEADER_NAME = "Authorization"
    }
}