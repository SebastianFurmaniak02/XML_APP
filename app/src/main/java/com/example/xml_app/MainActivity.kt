package com.example.xml_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.xml_app.fragments.FragmentCalculator
import com.example.xml_app.fragments.FragmentDatabase
import com.example.xml_app.fragments.FragmentForm
import com.example.xml_app.fragments.FragmentSensor
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNav = findViewById<BottomNavigationView>(R.id.BottomNavMenu)
        showFragment(FragmentCalculator())
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.calculator -> showFragment(FragmentCalculator())
                R.id.sensor -> showFragment(FragmentSensor())
                R.id.form -> showFragment(FragmentForm())
                R.id.database -> showFragment(FragmentDatabase())
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