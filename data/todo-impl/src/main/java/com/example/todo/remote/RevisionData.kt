package com.example.todo.remote

data class RevisionData<T>(val data: T, val revision: Int)