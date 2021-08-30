package com.example.carbcounter.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient_table")
data class Ingredient(
    @PrimaryKey @NonNull @ColumnInfo(name = "ingredientName") val ingredientName: String,
    @NonNull @ColumnInfo(name = "nutritionValue") val ingredientValue: String
)