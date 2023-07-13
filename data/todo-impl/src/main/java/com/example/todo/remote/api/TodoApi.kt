package com.example.todo.remote.api

import com.example.todo.remote.api.requests.SaveNewTodoRequest
import com.example.todo.remote.api.requests.UpdateTodoListRequest
import com.example.todo.remote.api.responses.OneItemResponse
import com.example.todo.remote.api.responses.TodoListResponse
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
   suspend fun fetchTodoList(): TodoListResponse

   @GET("list/{id}")
   suspend fun fetchOne(@Path("id") id: String): OneItemResponse

   @PATCH("list")
   suspend fun updateList(
      @Header("X-Last-Known-Revision") revision: Int,
      @Body updateTodoListRequest: UpdateTodoListRequest
   ): TodoListResponse

   @POST("list")
   suspend fun addNewTodo(
      @Header("X-Last-Known-Revision") revision: Int,
      @Body saveNewTodoRequest: SaveNewTodoRequest
   ): OneItemResponse

   @PUT("list/{id}")
   suspend fun editTodo(
      @Header("X-Last-Known-Revision") revision: Int,
      @Path("id") id: String,
      @Body saveNewTodoRequest: SaveNewTodoRequest
   ): OneItemResponse

   @DELETE("list/{id}")
   suspend fun delete(
      @Header("X-Last-Known-Revision") revision: Int,
      @Path("id") id: String
   ): OneItemResponse
}