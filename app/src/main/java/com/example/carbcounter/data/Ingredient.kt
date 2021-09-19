package com.example.carbcounter.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient_table", indices = [Index(value = [("ingredientName")],unique = true)])
data class Ingredient(
    @NonNull @ColumnInfo(name = "ingredientName") val ingredientName: String,
    @NonNull @ColumnInfo(name = "nutritionValue") val ingredientValue: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
/*{
    constructor(name: String, value: String) : this(0, name, value)
}*/