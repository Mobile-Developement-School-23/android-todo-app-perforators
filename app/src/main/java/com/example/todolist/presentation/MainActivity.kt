package com.example.todolist.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.example.todolist.R
import com.example.todolist.presentation.authorization.AuthorizationSharedViewModel
import com.yandex.authsdk.YandexAuthException
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthOptions
import com.yandex.authsdk.YandexAuthSdk
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var sdk: YandexAuthSdk
    private val authorizationViewModel: AuthorizationSharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sdk = YandexAuthSdk(this, YandexAuthOptions(this, loggingEnabled = true))

        lifecycleScope.launch {
            authorizationViewModel.authorizationEvent
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect {
                    val loginOptionsBuilder = YandexAuthLoginOptions.Builder()
                    val intent = sdk.createLoginIntent(loginOptionsBuilder.build())
                    startActivity(intent)
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_LOGIN_SDK) {
            try {
                val yandexAuthToken = sdk.extractToken(resultCode, data)
                if (yandexAuthToken != null) {
                    authorizationViewModel.sendToken(
                        AuthorizationSharedViewModel.AuthorizationResult.Success(sdk.getJwt(yandexAuthToken))
                    )
                }
            } catch (e: YandexAuthException) {
                authorizationViewModel.sendToken(
                    AuthorizationSharedViewModel.AuthorizationResult.Failure
                )
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val REQUEST_LOGIN_SDK = 1
    }
}