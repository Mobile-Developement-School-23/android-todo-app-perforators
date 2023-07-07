package com.example.todolist.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun observeAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo")
    suspend fun fetchAll(): List<TodoEntity>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun fetchBy(id: String): List<TodoEntity>

    @Insert
    suspend fun insertAll(entities: List<TodoEntity>)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteBy(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(entity: TodoEntity)

    @Query("DELETE FROM todo")
    suspend fun deleteAll()

    @Transaction
    suspend fun replaceAll(newEntities: List<TodoEntity>) {
        deleteAll()
        insertAll(newEntities)
    }
}