package com.example.authorization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.authorization.di.AuthorizationScreenScope
import com.example.authorization.navigation.AuthorizationNavCommandProvider
import com.example.domain.TokenRepository
import javax.inject.Inject

@AuthorizationScreenScope
class AuthorizationViewModelFactory @Inject constructor(
    private val repository: TokenRepository,
    private val navigation: AuthorizationNavCommandProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass == AuthorizationViewModel::class.java)
        return AuthorizationViewModel(repository, navigation) as T
    }
}