package com.example.xml_app.viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.xml_app.fragments.FragmentCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.sqrt

data class CalculatorScreenInfo(
    var screen: String = "0",
    var isDot: Boolean = false,
    var isLastSymbol: Boolean = false,
    var isResult: Boolean = true
)
class CalculatorViewModel : ViewModel() {
    private val _calculatorScreenInfo = MutableStateFlow(CalculatorScreenInfo())
    val calculatorScreenInfo: StateFlow<CalculatorScreenInfo> get() = _calculatorScreenInfo

    fun setNumberOnScreen(number: String) {

        val newInfo = _calculatorScreenInfo.value.copy()
        if (newInfo.screen == "0") {
            newInfo.screen = number
        } else {
            newInfo.screen += number
        }
        newInfo.isLastSymbol = false
        _calculatorScreenInfo.value = newInfo
    }

    fun setSymbolOnScreen(symbol: String) {
        val newInfo = _calculatorScreenInfo.value.copy()
        if (symbol == "." && !newInfo.isDot) {
            newInfo.isDot = true
            newInfo.isLastSymbol = false
            newInfo.screen += symbol
        } else if (symbol != "." && !newInfo.isLastSymbol) {
            newInfo.isResult = false
            newInfo.isDot = false
            newInfo.isLastSymbol = true
            newInfo.screen += symbol
        }
        _calculatorScreenInfo.value = newInfo
    }

    fun changeSign() {
        val newInfo = _calculatorScreenInfo.value.copy()
        if (newInfo.isResult) {
            var result = newInfo.screen.toDouble()
            result *= -1
            if (result % 1.0 == 0.0) {
                newInfo.screen = result.toInt().toString()
            } else {
                newInfo.screen = result.toString()
                newInfo.isDot = true
            }
        }
        _calculatorScreenInfo.value = newInfo
    }

    fun clearScreen() {
        val newInfo = _calculatorScreenInfo.value.copy()
        newInfo.screen = "0"
        newInfo.isResult = true
        newInfo.isDot = false
        newInfo.isLastSymbol = false
        _calculatorScreenInfo.value = newInfo
    }

    fun squareRoot() {
        val newInfo = _calculatorScreenInfo.value.copy()
        var result = newInfo.screen.toDouble()
        if (newInfo.isResult && result > 0) {
            result = sqrt(result)
            if (result % 1.0 == 0.0) {
                newInfo.screen = result.toInt().toString()
            } else {
                newInfo.screen = result.toString()
                newInfo.isDot = true
            }
        }
        _calculatorScreenInfo.value = newInfo
    }

    fun square() {
        val newInfo = _calculatorScreenInfo.value.copy()
        if (newInfo.isResult) {
            var result = newInfo.screen.toDouble()
            result *= result
            if (result % 1.0 == 0.0) {
                newInfo.screen = result.toInt().toString()
            } else {
                newInfo.screen = result.toString()
                newInfo.isDot = true
            }
        }
        _calculatorScreenInfo.value = newInfo
    }

    fun calculateResult(){

        if (_calculatorScreenInfo.value.isLastSymbol || _calculatorScreenInfo.value.isResult) return

        var screen = _calculatorScreenInfo.value.screen
            .replace("--","-~")
            .replace("+-","+~")
            .replace("/-","/~")
            .replace("*-","*~")
        if (screen.startsWith("-")) {
            screen = screen.removePrefix("-")
            screen = "~$screen"
        }
        val numbers = screen.split("+", "-", "*", "/").filter { it.isNotEmpty() }.toMutableList()
        val operators = screen.split(Regex("[~0-9.]+")).filter { it.isNotEmpty() }.toMutableList()
        var result = 0.0
        var index = 0
        for (i in numbers.indices) {
            numbers[i] = numbers[i].replace("~", "-")
        }

        while (index != -1) {
            index = operators.indexOfFirst { it in setOf("*", "/") }
            if (index != -1) {
                val number1 = numbers[index].toDouble()
                val number2 = numbers[index + 1].toDouble()
                result = if (operators[index] == "*") {
                    number1 * number2
                } else {
                    if (number2 == 0.0) {
                        clearScreen()
                        _calculatorScreenInfo.value.screen = Double.NaN.toString()
                        return
                    }
                    number1 / number2
                }
                operators.removeAt(index)
                numbers.removeAt(index)
                numbers[index] = result.toString()
            }
        }
        index = 0
        while (index != -1) {
            index = operators.indexOfFirst { it in setOf("+", "-") }
            if (index != -1) {
                val number1 = numbers[index].toDouble()
                val number2 = numbers[index + 1].toDouble()
                result = if (operators[index] == "+") {
                    number1 + number2
                } else {
                    number1 - number2
                }
                operators.removeAt(index)
                numbers.removeAt(index)
                numbers[index] = result.toString()
            }
        }

        clearScreen()
        val newInfo = _calculatorScreenInfo.value.copy()
        if (result % 1.0 == 0.0) {
            newInfo.screen = result.toInt().toString()
        } else {
            newInfo.screen = result.toString()
            newInfo.isDot = true
        }
        _calculatorScreenInfo.value = newInfo
    }
}
