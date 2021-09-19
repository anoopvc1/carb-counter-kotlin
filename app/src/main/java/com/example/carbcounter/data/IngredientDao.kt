package com.example.carbcounter.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredient_table ORDER BY ingredientName ASC")
    fun getAll(): Flow<List<Ingredient>>

    @Insert(entity = Ingredient::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: Ingredient)

    @Update(entity = Ingredient::class)
    suspend fun update(ingredient: Ingredient)

    @Delete(entity = Ingredient::class)
    suspend fun delete(ingredient: Ingredient)

    @Query("DELETE FROM ingredient_table")
    suspend fun deleteAll()
}