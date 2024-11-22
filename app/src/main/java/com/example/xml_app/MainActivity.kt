package com.example.xml_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.xml_app.fragments.FragmentCalculator
import com.example.xml_app.fragments.FragmentDatabase
import com.example.xml_app.fragments.FragmentSensor
import com.example.xml_app.fragments.FragmentStatistics
import com.example.xml_app.viewModel.BottomBarViewModel
import com.example.xml_app.viewModel.CalculatorViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel = ViewModelProvider(this)[BottomBarViewModel::class.java]

        Log.i("USER_LOG",viewModel.currentFragment.value.toString())
        val bottomNav = findViewById<BottomNavigationView>(R.id.BottomNavMenu)
        viewModel.currentFragment.value?.let { showFragment(it) }
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.calculator -> {
                    showFragment(FragmentCalculator())
                    viewModel.updateFragment(FragmentCalculator())
                }
                R.id.sensor -> {
                    showFragment(FragmentSensor())
                    viewModel.updateFragment(FragmentSensor())
                }
                R.id.database -> {
                    showFragment(FragmentDatabase())
                    viewModel.updateFragment(FragmentDatabase())
                }
                R.id.statistics -> {
                    showFragment(FragmentStatistics())
                    viewModel.updateFragment(FragmentStatistics())
                }
            }
            true
        }
    }


    private fun showFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}