package com.example.todolist.data.remote.api

import com.example.todolist.data.remote.api.requests.UpdateTodoListRequest
import com.example.todolist.data.remote.api.responses.TodoListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH

interface TodoApi {

   @GET("list")
   suspend fun fetchTodoList(): Response<TodoListResponse>

   @PATCH("list")
   suspend fun updateList(
      @Header("X-Last-Known-Revision") revision: Int,
      @Body updateTodoListRequest: UpdateTodoListRequest
   )
}