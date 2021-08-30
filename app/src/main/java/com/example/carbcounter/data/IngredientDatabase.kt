package com.example.carbcounter.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Ingredient::class], version = 1, exportSchema = false)
abstract class IngredientDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    /*companion object {
        private var INSTANCE: IngredientDatabase? = null

        fun getDatabase(context: Context): IngredientDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance =
                    Room.databaseBuilder(context.applicationContext, IngredientDatabase::class.java, "ingredient_database.db")
                        .createFromAsset("ingredient_database.db")
                        .build()
                INSTANCE = instance
                instance
            }
        }
    }*/
}