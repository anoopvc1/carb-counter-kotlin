package com.example.carbcounter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val viewModel: IngredientViewModel by activityViewModels()
    private var ingredientNames = mutableListOf<String>()
    private lateinit var ingredientList: List<Ingredient>
    private var selectedIng: Ingredient? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditdbBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = IngredientAdapter()
        binding.itemRecycler.adapter = adapter
        binding.itemRecycler.layoutManager = LinearLayoutManager(requireContext())

        viewModel.ingredients.observe(viewLifecycleOwner, Observer {
            //needs optimisation
            ingredientNames.clear()
            ingredientList = listOf()
            ingredientList = it
            it.forEach { ingredient ->
                ingredientNames.add(ingredient.ingredientName)
            }
            adapter.setListItems(it)
            val arrayAdapter =
                ArrayAdapter(requireContext(), R.layout.autocomplete_dropdown_item, ingredientNames)
            binding.searchIngredient.setAdapter(arrayAdapter)
            //why threshold 0 not working
            binding.searchIngredient.threshold = 0
            binding.update.isVisible = false
            binding.delete.isVisible = false

        })

        binding.searchIngredient.setOnItemClickListener { parent, view, position, id ->
            //optimize
            selectedIng =
                ingredientList.find { it.ingredientName == binding.searchIngredient.text.toString() }
            binding.ingredientName.setText(selectedIng?.ingredientName)
            binding.nutritionalVal.setText(selectedIng?.ingredientValue)
            binding.add.isVisible = false
            binding.update.isVisible = true
            binding.delete.isVisible = true
        }

        binding.clear.setOnClickListener {
            //optimize
            binding.searchIngredient.text = null
            binding.ingredientName.text = null
            binding.nutritionalVal.text = null
            binding.add.isVisible = true
            binding.update.isVisible = false
            binding.delete.isVisible = false
        }

        binding.add.setOnClickListener {
            if (!binding.ingredientName.text.isNullOrEmpty() && !binding.nutritionalVal.text.isNullOrEmpty()) {
                viewModel.insert(
                    Ingredient(
                        binding.ingredientName.text.toString(),
                        binding.nutritionalVal.text.toString()
                    )
                )
                //verify for success - pending
                Toast.makeText(requireContext(), "item added", Toast.LENGTH_SHORT).show()
            }
        }

        binding.update.setOnClickListener {
            if (!binding.ingredientName.text.isNullOrEmpty() && !binding.nutritionalVal.text.isNullOrEmpty()) {
                //optimize
                if (selectedIng != null) {
                    viewModel.update(
                        Ingredient(
                            binding.ingredientName.text.toString(),
                            binding.nutritionalVal.text.toString(),
                            selectedIng!!.id
                        )
                    )
                    //verify for success - pending
                    Toast.makeText(requireContext(), "item updated", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(requireContext(), "update unsuccessful", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.delete.setOnClickListener {
            if (!binding.ingredientName.text.isNullOrEmpty() && !binding.nutritionalVal.text.isNullOrEmpty()) {
                //optimize
                if (selectedIng != null) {
                    viewModel.delete(selectedIng!!)
                    //verify for success - pending
                    Toast.makeText(requireContext(), "item deleted", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(requireContext(), "delete unsuccessful", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}