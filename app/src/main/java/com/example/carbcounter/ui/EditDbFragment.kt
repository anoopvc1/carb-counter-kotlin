package com.example.carbcounter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.carbcounter.IngredientViewModel
import com.example.carbcounter.R
import com.example.carbcounter.data.Ingredient
import com.example.carbcounter.databinding.FragmentEditdbBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditDbFragment : Fragment() {

    private var _binding: FragmentEditdbBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private val viewModel : IngredientViewModel by activityViewModels()
    private var ingredientNames = mutableListOf<String>()
    private var ingredients = mutableMapOf<String, String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditdbBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.ingredients.observe(viewLifecycleOwner, Observer {
                //needs optimisation
                ingredients.clear()
                ingredientNames.clear()
                it.forEach { ingredient ->
                    ingredientNames.add(ingredient.ingredientName)
                    ingredients.put(ingredient.ingredientName, ingredient.ingredientValue)
                }
                val adapter = ArrayAdapter(requireContext(), R.layout.autocomplete_dropdown_item, ingredientNames)
                binding.searchIngredient.setAdapter(adapter)
                //why threshold 0 not working
                binding.searchIngredient.threshold = 0
        })

        val adapter = ArrayAdapter(requireContext(), R.layout.autocomplete_dropdown_item, ingredientNames)
        binding.searchIngredient.setAdapter(adapter)
        //why threshold 0 not working
        binding.searchIngredient.threshold = 0

        binding.searchIngredient.setOnItemClickListener { parent, view, position, id ->
            binding.ingredientName.setText(binding.searchIngredient.text.toString())
            binding.nutritionalVal.setText(ingredients.get(binding.searchIngredient.text.toString()))
        }

        binding.add.setOnClickListener {
            if (!binding.ingredientName.text.isNullOrEmpty() && !binding.nutritionalVal.text.isNullOrEmpty()){
                viewModel.insert(Ingredient(binding.ingredientName.text.toString(), binding.nutritionalVal.text.toString()))
                //verify for success - pending
                Toast.makeText(requireContext(), "item added", Toast.LENGTH_SHORT).show()
            }
        }

        binding.delete.setOnClickListener {
            if (!binding.ingredientName.text.isNullOrEmpty() && !binding.nutritionalVal.text.isNullOrEmpty()){
                viewModel.delete(Ingredient(binding.ingredientName.text.toString(), binding.nutritionalVal.text.toString()))
                //verify for success - pending
                Toast.makeText(requireContext(), "item deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}