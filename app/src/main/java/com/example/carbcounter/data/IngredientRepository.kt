package com.example.carbcounter.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientRepository @Inject constructor(private val dao: IngredientDao) {

    val allIngredients: Flow<List<Ingredient>> = dao.getAll()

    suspend fun insert(ingredient: Ingredient) {
        dao.insert(ingredient)
    }

    suspend fun update(ingredient: Ingredient) {
        dao.update(ingredient)
    }

    suspend fun delete(ingredient: Ingredient) {
        dao.delete(ingredient)
    }

}