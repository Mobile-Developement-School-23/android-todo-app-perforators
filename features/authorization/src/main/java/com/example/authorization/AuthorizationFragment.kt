package com.example.authorization

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.authorization.databinding.FragmentAuthorizationBinding
import com.example.authorization.di.AuthorizationDepsStore
import com.example.authorization.di.DaggerAuthorizationScreenComponent
import com.example.navigation.navigate
import com.example.utils.showToast
import com.yandex.authsdk.YandexAuthException
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthSdk
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    @Inject
    internal lateinit var factory: AuthorizationViewModelFactory

    @Inject
    internal lateinit var yandexSdk: YandexAuthSdk

    private val authorizationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_LOGIN_SDK) handleToken(it.resultCode, it.data)
        }

    private val binding: FragmentAuthorizationBinding by viewBinding(
        FragmentAuthorizationBinding::bind
    )
    private val viewModel: AuthorizationViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAuthorizationScreenComponent.factory()
            .create(AuthorizationDepsStore.deps)
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.token.setOnClickListener {
            viewModel.addDefaultToken()
        }
        binding.yandexId.setOnClickListener {
            authorize()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect(::handle)
        }
    }

    private fun authorize() {
        val loginOptionsBuilder = YandexAuthLoginOptions.Builder()
        val intent = yandexSdk.createLoginIntent(loginOptionsBuilder.build())
        authorizationLauncher.launch(intent)
    }

    private fun handle(event: AuthorizationViewModel.Event) {
        when (event) {
            is AuthorizationViewModel.Event.ShowAllTodoItems -> navigate(event.command)
        }
    }

    private fun handleToken(resultCode: Int, data: Intent?) {
        try {
            yandexSdk.extractToken(resultCode, data)?.let {
                viewModel.addYandexToken(it.value)
            }
        } catch (e: YandexAuthException) {
            showToast(requireContext().getString(R.string.not_authorized))
        }
    }

    companion object {
        private const val RESULT_LOGIN_SDK = 1
    }
}