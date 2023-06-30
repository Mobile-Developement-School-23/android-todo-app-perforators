package com.example.todolist.di.detail

import com.example.todolist.presentation.detail.DetailFragment
import dagger.Component

@DetailScreenScope
@Component
interface DetailScreenComponent {

    fun inject(fragment: DetailFragment)
}