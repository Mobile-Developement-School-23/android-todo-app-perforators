package com.example.data.remote

import com.example.domain.models.TodoItem

interface TodoRemoteDataSource {

    suspend fun fetchTodoList(): NetworkResult<RevisionData<List<TodoItem>>>

    suspend fun fetchOne(id: String): NetworkResult<RevisionData<TodoItem>>

    suspend fun updateTodoList(
        currentList: List<TodoItem>,
        lastKnownRevision: Int
    ): NetworkResult<RevisionData<List<TodoItem>>>

    suspend fun addNew(revision: Int, item: TodoItem): NetworkResult<RevisionData<TodoItem>>

    suspend fun edit(revision: Int, item: TodoItem): NetworkResult<RevisionData<TodoItem>>

    suspend fun delete(revision: Int, id: String): NetworkResult<RevisionData<TodoItem>>
}