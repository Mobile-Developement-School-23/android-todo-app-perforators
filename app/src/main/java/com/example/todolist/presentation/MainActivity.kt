package com.example.todolist.presentation

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
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

    private val authorizationViewModel: AuthorizationSharedViewModel by viewModels()
    private lateinit var yandexSdk: YandexAuthSdk

    private val authorizationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_LOGIN_SDK) {
                try {
                    val yandexAuthToken = yandexSdk.extractToken(it.resultCode, it.data)
                    if (yandexAuthToken != null) {
                        authorizationViewModel.send(
                            AuthorizationSharedViewModel.AuthorizationResult.Success(
                                yandexSdk.getJwt(yandexAuthToken)
                            )
                        )
                    }
                } catch (e: YandexAuthException) {
                    authorizationViewModel.send(
                        AuthorizationSharedViewModel.AuthorizationResult.Failure
                    )
                }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        yandexSdk = YandexAuthSdk(this, YandexAuthOptions(this))

        lifecycleScope.launch {
            authorizationViewModel.authorizationEvent
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { authorize() }
        }
    }

    private fun authorize() {
        val loginOptionsBuilder = YandexAuthLoginOptions.Builder()
        val intent = yandexSdk.createLoginIntent(loginOptionsBuilder.build())
        authorizationLauncher.launch(intent)
    }

    companion object {
        private const val RESULT_LOGIN_SDK = 1
    }
}