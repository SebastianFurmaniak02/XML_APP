package com.example.xml_app.viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xml_app.fragments.FragmentCalculator

class BottomBarViewModel : ViewModel() {
    private val _currentFragment = MutableLiveData<Fragment>(FragmentCalculator())
    val currentFragment: LiveData<Fragment> get() = _currentFragment
    fun updateFragment(fragment: Fragment) {
        _currentFragment.value = fragment
    }
}