package com.example.edittodo.di

import com.example.domain.TodoItemsRepository
import kotlin.properties.Delegates.notNull

interface DetailScreenDependencies {

    fun repository(): TodoItemsRepository
}

interface DetailDepsProvider {
    val deps: DetailScreenDependencies

    companion object : DetailDepsProvider by DetailDepsStore
}

object DetailDepsStore : DetailDepsProvider {

    override var deps: DetailScreenDependencies by notNull()
}