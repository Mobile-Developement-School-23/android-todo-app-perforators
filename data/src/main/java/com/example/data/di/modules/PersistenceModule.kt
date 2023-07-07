package com.example.data.di.modules

import android.content.Context
import androidx.room.Room
import com.example.data.local.TodoDao
import com.example.data.local.TodoDatabase
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