package com.example.simulatedannealing

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.simulatedannealing.fragments.AbstractFragment


class MainActivity : AbstractFragment.FragmentListener, AppCompatActivity() {
    private var currentFragment = 0
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)
        currentFragment = R.string.main_window;
    }

    override fun changeFragment(newFragment: Fragment?, fragmentClassName: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
                R.id.fragmentContainerView,
                newFragment!!,
                resources.getString(fragmentClassName)
        )
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.addToBackStack(getString(fragmentClassName)).commit()
        currentFragment = fragmentClassName
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.home -> {
            true
        }
        else -> {
            onBackPressed()
            super.onOptionsItemSelected(item)
        }
    }

    fun showProgress() {
        progressDialog = ProgressDialog.show(this, "", getString(R.string.preloader))
        progressDialog?.setCancelable(false)
    }

    fun hideProgress() {
        progressDialog!!.dismiss()
    }
}