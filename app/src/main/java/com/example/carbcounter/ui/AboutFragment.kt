package com.example.carbcounter.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class AboutFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("WELCOME TO CARB COUNTER")
            .setMessage("Eating right is simple. But not easy. Consistency and perseverence will get you there. Never lose hope if you miss 1 or 2 sessions. It matters more how quickly you can get back into it than that you deviated from your path \n\nDisclaimer: Nutritional infromation available here is from online sources and not medically certified. If you have specific requirements and/or have a medical condition, please seek expert advice.\n\nAgree to continue.")
            .setPositiveButton("AGREE") { _, _ ->
                val sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("TERMS", true)
                    apply()
                }
            }
            .setNegativeButton("DECLINE") { _, _ -> requireActivity().finishAffinity() }
            .create()
        //prevent 'back' button from dismissing the dialog
        isCancelable = false
        //prevent cancelling on touching outside dialog bounds
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    //control dialog dimensions
    /*override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
    }*/

    companion object {
        const val TAG = "AboutDialog"
    }
}