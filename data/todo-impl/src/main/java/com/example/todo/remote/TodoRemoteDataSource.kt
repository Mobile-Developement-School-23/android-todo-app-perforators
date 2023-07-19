package com.example.todo.remote

import com.example.todo_api.models.TodoItem

interface TodoRemoteDataSource {

    suspend fun fetchTodoList(): Result<RevisionData<List<TodoItem>>>

    suspend fun fetchOne(id: String): Result<RevisionData<TodoItem>>

    suspend fun updateTodoList(
        currentList: List<TodoItem>,
        lastKnownRevision: Int
    ): Result<RevisionData<List<TodoItem>>>

    suspend fun addNew(revision: Int, item: TodoItem): Result<RevisionData<TodoItem>>

    suspend fun edit(revision: Int, item: TodoItem): Result<RevisionData<TodoItem>>

    suspend fun delete(revision: Int, id: String): Result<RevisionData<TodoItem>>
}