package com.example.carbcounter.di

import android.content.Context
import androidx.room.Room
import com.example.carbcounter.data.IngredientDao
import com.example.carbcounter.data.IngredientDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    //Tell hilt how to inject dao
    @Provides
    fun provideDao(database: IngredientDatabase): IngredientDao {
        return database.ingredientDao()
    }

    //tell hilt how to inject db
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): IngredientDatabase {
        return Room.databaseBuilder(appContext,
            IngredientDatabase::class.java,
            "ingredient_database.db"
        ).createFromAsset("ingredient_database.db").build()
    }
}