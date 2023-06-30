package com.example.todolist.data.remote

data class RevisionData<T>(val data: T, val revision: Int)