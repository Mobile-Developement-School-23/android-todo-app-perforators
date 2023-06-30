package com.example.todolist.data.remote

import com.example.todolist.domain.models.TodoItem

interface TodoRemoteDataSource {

    suspend fun fetchTodoList(): NetworkResult<RevisionData<List<TodoItem>>>

    suspend fun fetchOne(id: String): NetworkResult<RevisionData<TodoItem>>

    suspend fun updateTodoList(
        currentList: List<TodoItem>,
        lastKnownRevision: Int
    ): NetworkResult<RevisionData<List<TodoItem>>>
}