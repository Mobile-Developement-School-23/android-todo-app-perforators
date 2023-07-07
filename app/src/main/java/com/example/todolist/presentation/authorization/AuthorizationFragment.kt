package com.example.todolist.presentation.authorization

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.todolist.R
import com.example.todolist.appComponent
import com.example.todolist.databinding.FragmentAuthorizationBinding
import com.example.todolist.di.authentication.DaggerAuthorizationScreenComponent
import com.example.todolist.utils.showToast
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {

    @Inject
    internal lateinit var factory: AuthorizationViewModelFactory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerAuthorizationScreenComponent.factory()
            .create(appComponent())
            .inject(this)
    }

    private val binding: FragmentAuthorizationBinding by viewBinding(
        FragmentAuthorizationBinding::bind
    )
    private val viewModel: AuthorizationViewModel by viewModels { factory }
    private val authorizationSharedViewModel: AuthorizationSharedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.token.setOnClickListener {
            viewModel.addDefaultToken()
        }
        binding.yandexId.setOnClickListener {
            authorizationSharedViewModel.authorize()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.events
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect(::handle)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            authorizationSharedViewModel.authorizationResult
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    if (it is AuthorizationSharedViewModel.AuthorizationResult.Success) {
                        viewModel.addYandexToken(it.token)
                    } else {
                        showToast(requireContext().getString(R.string.not_authorized))
                    }
                }
        }
    }

    private fun handle(event: AuthorizationViewModel.Event) {
        when (event) {
            is AuthorizationViewModel.Event.ShowAllTodoItems -> findNavController()
                .navigate(
                    AuthorizationFragmentDirections.actionAuthorizationFragmentToTodoListFragment()
                )
        }
    }
}