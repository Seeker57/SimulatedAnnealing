package com.example.simulatedannealing.fragments

import android.R
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class AbstractFragment(layout: Int) : Fragment(layout) {

     interface FragmentListener {
        fun changeFragment(newFragment: Fragment?, fragmentClassName: Int);
        fun onBackPressed();
     }

    lateinit var fragmentListener: FragmentListener;

    override fun onAttach(context: Context) {
        super.onAttach(context);
        try {
            fragmentListener = context as FragmentListener;
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must be implements interface FragmentListener");
        }
    }

    fun showMessage(title: String, message: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.ok, null)
        builder.create().show()
    }

    abstract override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View;

    abstract fun setElementsUI(view: View);

    abstract fun setActionBar();
}