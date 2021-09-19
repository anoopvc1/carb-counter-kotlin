package com.example.carbcounter.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carbcounter.R
import com.example.carbcounter.data.Ingredient
import java.util.zip.Inflater

class IngredientAdapter: RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

    private var listItems: List<Ingredient> = listOf()

    fun setListItems(items: List<Ingredient>){
        listItems = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_item, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.name.text = listItems[position].ingredientName
        holder.value.text = listItems[position].ingredientValue
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.recycler_item)
        val value: TextView = itemView.findViewById(R.id.recycler_value)
    }
}