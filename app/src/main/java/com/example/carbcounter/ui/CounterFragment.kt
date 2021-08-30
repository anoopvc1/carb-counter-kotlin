package com.example.carbcounter.ui

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.carbcounter.IngredientViewModel
import com.example.carbcounter.R
import com.example.carbcounter.data.Ingredient
import com.example.carbcounter.databinding.FragmentCounterBinding
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CounterFragment : Fragment() {

    private var _binding: FragmentCounterBinding? = null
    private var ingredientNames = mutableListOf<String>()
    private var ingredients = mutableMapOf<String, String>()
    private val viewModel: IngredientViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var row = 0
    private lateinit var rowHolderLinLayout: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCounterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        rowHolderLinLayout = binding.itemParent
        createRow()
        //alert dialog code - create dialog fragment similar to this
        /*AlertDialog mdb = new MaterialAlertDialogBuilder(this)
            .setTitle("WELCOME TO CARB COUNTER")
            .setMessage("Doing this will remove the error but not get him to where he wants to be which is an activity with a dialog theme. The general rule is that if you want your activity to have an action bar it should have the AppCompat theme and the java code should extend ActionBarActivity. If you have an activity that doesn't need an action bar (like a dialog themed activity) you can apply any theme to it but the java code must extend plain old activity")
            .setNeutralButton("DISMISS", (dialog, which) -> Log.d("tag", "msg"))
        .setPositiveButton("AGREE", (dialog, which) -> Log.d("tag", "msg"))
        .create();
        mdb.show();
        mdb.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);*/
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener { createRow() }
        viewModel.ingredients.observe(viewLifecycleOwner, Observer {
            //optimize this part later
            ingredients.clear()
            ingredientNames.clear()
            it.forEach { ingredient ->
                ingredientNames.add(ingredient.ingredientName)
                ingredients.put(ingredient.ingredientName, ingredient.ingredientValue)
            }
        })
        binding.totalButton.setOnClickListener { carbTotal() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_counter, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_refresh -> {
                //reset back to a single row
                rowHolderLinLayout.removeAllViews()
                row = 0
                createRow()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //create ingredient row onCreate and on click of 'plus' button
    private fun createRow() {
        //set card view
        val cardView = MaterialCardView(context)
        val cardViewMarginParams = ViewGroup.MarginLayoutParams(
            ViewGroup.MarginLayoutParams.MATCH_PARENT,
            ViewGroup.MarginLayoutParams.MATCH_PARENT
        )
        cardViewMarginParams.setMargins(12, 10, 12, 10)
        cardView.layoutParams = cardViewMarginParams
        cardView.elevation = 8F
        //cardView.useCompatPadding = true
        //cardView.requestLayout()
        cardView.radius = 10F
        cardView.setCardBackgroundColor(Color.parseColor("#5F676E"))

        //set linear layout
        val linLayout = LinearLayout(context)
        linLayout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        linLayout.orientation = LinearLayout.HORIZONTAL
        linLayout.gravity = Gravity.CENTER_HORIZONTAL
        linLayout.gravity = Gravity.CENTER_VERTICAL
        linLayout.minimumHeight = 150

        val param1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        val hintColor = Color.parseColor("#C5C6C8")
        val textColor = Color.WHITE

        //add field views
        val ingredient = AutoCompleteTextView(context)
        ingredient.setHint("DISH NAME")
        ingredient.setTag("ingredient$row")
        ingredient.inputType = InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        ingredient.background = null
        ingredient.textAlignment = View.TEXT_ALIGNMENT_CENTER
        ingredient.textSize = 14F
        ingredient.width = 150
        ingredient.filters = arrayOf(InputFilter.AllCaps())
        ingredient.setHintTextColor(hintColor)
        ingredient.setTextColor(textColor)

        //add autocompleteview ingredients suggestions
        val adapter =
            ArrayAdapter(requireContext(), R.layout.autocomplete_dropdown_item, ingredientNames)
        ingredient.setAdapter(adapter)
        //why threshold 0 not working
        ingredient.threshold = 0

        val serving = EditText(context)
        serving.hint = "SERVING"
        serving.tag = "serving$row"
        serving.inputType = InputType.TYPE_CLASS_NUMBER
        serving.background = null
        serving.textAlignment = View.TEXT_ALIGNMENT_CENTER
        serving.textSize = 14f
        serving.setHintTextColor(hintColor)
        serving.setTextColor(textColor)

        val nutrition = TextView(context)
        nutrition.hint = "0"
        nutrition.tag = "nutrition$row"
        nutrition.textAlignment = View.TEXT_ALIGNMENT_CENTER
        nutrition.textSize = 14f
        nutrition.setHintTextColor(hintColor)
        nutrition.setTextColor(textColor)

        val result = TextView(context)
        result.hint = "0"
        result.tag = "result$row"
        result.textAlignment = View.TEXT_ALIGNMENT_CENTER
        result.textSize = 14f
        result.setHintTextColor(hintColor)
        result.setTextColor(textColor)

        //add view listeners (add focus change listener to clear values?)
        ingredient.setOnItemClickListener { parent, view, position, id ->
            nutrition.text = ingredients.get(ingredient.text.toString())
            if (!serving.text.isNullOrEmpty()) {
                carbCount(nutrition, serving, result)
            }
        }

        serving.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!nutrition.text.isNullOrEmpty() && !serving.text.isNullOrEmpty()) {
                    carbCount(nutrition, serving, result)
                }
            }
        })

        //add views to layout
        linLayout.addView(ingredient, param1)
        linLayout.addView(serving, param1)
        linLayout.addView(nutrition, param1)
        linLayout.addView(result, param1)

        //add linear layout to root
        cardView.addView(linLayout)
        rowHolderLinLayout.addView(cardView)
        ingredient.requestFocus()
        row++
    }

    private fun carbCount(
        nutrition: TextView,
        serving: EditText,
        result: TextView
    ) {
        val int1 = nutrition.text.toString().toFloat()
        val int2 = serving.text.toString().toFloat()
        val resultFloat = int1 * int2
        //set count result
        result.text = String.format("%.2f", resultFloat)
    }

    //calculate total carbs
    private fun carbTotal() {
        val parent = binding.itemParent
        var total = 0f
        repeat(row) { index ->
            val indResult = parent.findViewWithTag<TextView>("result$index")
            if (indResult != null && indResult.length() > 0) {
                total += indResult.text.toString().toFloat()
            }
        }
        //refactor
        binding.total.text = String.format("%.2f", total) + "g"
    }
}