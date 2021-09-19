package com.example.carbcounter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.carbcounter.data.Ingredient
import com.example.carbcounter.data.IngredientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(private val repository: IngredientRepository) :
    ViewModel() {

    val ingredients = repository.allIngredients.asLiveData()

    fun insert(ingredient: Ingredient) = viewModelScope.launch { repository.insert(ingredient) }

    fun update(ingredient: Ingredient) = viewModelScope.launch { repository.update(ingredient) }

    fun delete(ingredient: Ingredient) = viewModelScope.launch { repository.delete(ingredient) }
}

