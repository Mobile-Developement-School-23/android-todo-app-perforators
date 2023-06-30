package com.example.todolist.data.remote.api

import com.example.todolist.data.remote.api.requests.SaveNewTodoRequest
import com.example.todolist.data.remote.api.requests.UpdateTodoListRequest
import com.example.todolist.data.remote.api.responses.OneItemResponse
import com.example.todolist.data.remote.api.responses.TodoListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {

   @GET("list")
   suspend fun fetchTodoList(): Response<TodoListResponse>

   @GET("list/{id}")
   suspend fun fetchOne(@Path("id") id: String): Response<OneItemResponse>

   @PATCH("list")
   suspend fun updateList(
      @Header("X-Last-Known-Revision") revision: Int,
      @Body updateTodoListRequest: UpdateTodoListRequest
   ): Response<TodoListResponse>

   @POST("list")
   suspend fun addNewTodo(
      @Body saveNewTodoRequest: SaveNewTodoRequest
   ): Response<OneItemResponse>

   @PUT("list/{id}")
   suspend fun editTodo(
      @Path("id") id: String,
      @Body saveNewTodoRequest: SaveNewTodoRequest
   ): Response<OneItemResponse>

   @DELETE("list/{id}")
   suspend fun delete(@Path("id") id: String): Response<OneItemResponse>
}