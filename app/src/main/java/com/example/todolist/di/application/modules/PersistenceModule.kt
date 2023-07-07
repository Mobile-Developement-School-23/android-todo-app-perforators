package com.example.todolist.di.application.modules

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.local.database.TodoDao
import com.example.todolist.data.local.database.TodoDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context,
            TodoDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao = database.todoDao()

    companion object {

        private const val DB_NAME = "App database"
    }
}