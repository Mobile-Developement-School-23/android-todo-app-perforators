package com.example.di

import com.example.edittodo.DetailFragment
import com.example.notification.DelayNotificationReceiver
import com.example.notification.NotifyReceiver
import com.example.notification.additionalscreen.NotifyDialogFragment
import com.example.todolist.TodoListFragment
import dagger.Component

@TodoFeatureScope
@Component(
    dependencies = [TodoFeatureDependencies::class]
)
interface TodoFeatureComponent {

    fun inject(fragment: TodoListFragment)

    fun inject(fragment: DetailFragment)

    fun inject(fragment: NotifyDialogFragment)

    fun inject(receiver: NotifyReceiver)

    fun inject(receiver: DelayNotificationReceiver)

    @Component.Factory
    interface Factory {

        fun create(dependencies: TodoFeatureDependencies): TodoFeatureComponent
    }
}