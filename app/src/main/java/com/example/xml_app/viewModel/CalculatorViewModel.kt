package com.example.xml_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class CalculatorScreenInfo(
    var screen: String = "0",
    var isDot: Boolean = false,
    var isLastSymbol: Boolean = false,
    var isResult: Boolean = true
)
class CalculatorViewModel : ViewModel() {
    private val _text = MutableLiveData("Hello, ViewModel!")
    val text: LiveData<String> get() = _text
}
