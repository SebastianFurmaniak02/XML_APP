package com.example.xml_app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.xml_app.R
import kotlin.math.sqrt

class FragmentCalculator : Fragment(), View.OnClickListener {

    private lateinit var button0 : Button
    private lateinit var button1 : Button
    private lateinit var button2 : Button
    private lateinit var button3 : Button
    private lateinit var button4 : Button
    private lateinit var button5 : Button
    private lateinit var button6 : Button
    private lateinit var button7 : Button
    private lateinit var button8 : Button
    private lateinit var button9 : Button
    private lateinit var buttonPlus : Button
    private lateinit var buttonMinus : Button
    private lateinit var buttonMultiplication : Button
    private lateinit var buttonDivision : Button
    private lateinit var buttonSquare : Button
    private lateinit var buttonSquareRoot : Button
    private lateinit var buttonDot : Button
    private lateinit var buttonSign : Button
    private lateinit var buttonClear : Button
    private lateinit var buttonEqual : Button
    private lateinit var calculatorScreen : TextView
    private var isDot : Boolean = false
    private var isResult : Boolean = true
    private var isLastSymbol : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button0 = setButtonListener(R.id.calculator_0)
        button1 = setButtonListener(R.id.calculator_1)
        button2 = setButtonListener(R.id.calculator_2)
        button3 = setButtonListener(R.id.calculator_3)
        button4 = setButtonListener(R.id.calculator_4)
        button5 = setButtonListener(R.id.calculator_5)
        button6 = setButtonListener(R.id.calculator_6)
        button7 = setButtonListener(R.id.calculator_7)
        button8 = setButtonListener(R.id.calculator_8)
        button9 = setButtonListener(R.id.calculator_9)
        buttonPlus = setButtonListener(R.id.calculator_plus)
        buttonMinus = setButtonListener(R.id.calculator_minus)
        buttonMultiplication = setButtonListener(R.id.calculator_multiplication)
        buttonDivision = setButtonListener(R.id.calculator_division)
        buttonSquare = setButtonListener(R.id.calculator_square)
        buttonSquareRoot = setButtonListener(R.id.calculator_square_root)
        buttonDot = setButtonListener(R.id.calculator_dot)
        buttonSign = setButtonListener(R.id.calculator_sign)
        buttonClear = setButtonListener(R.id.calculator_clear)
        buttonEqual = setButtonListener(R.id.calculator_equal)
        calculatorScreen = requireView().findViewById(R.id.calculator_screen)
        calculatorScreen.text = "0"

    }

    private fun setButtonListener(id: Int): Button {
        val newButton = requireView().findViewById<Button>(id)
        newButton.setOnClickListener(this)
        return newButton
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        v?.let {button ->
            when (button.id) {
                R.id.calculator_0 -> {
                    setNumberOnScreen("0")
                }
                R.id.calculator_1 -> {
                    setNumberOnScreen("1")
                }
                R.id.calculator_2 -> {
                    setNumberOnScreen("2")
                }
                R.id.calculator_3 -> {
                    setNumberOnScreen("3")
                }
                R.id.calculator_4 -> {
                    setNumberOnScreen("4")
                }
                R.id.calculator_5 -> {
                    setNumberOnScreen("5")
                }
                R.id.calculator_6 -> {
                    setNumberOnScreen("6")
                }
                R.id.calculator_7 -> {
                    setNumberOnScreen("7")
                }
                R.id.calculator_8 -> {
                    setNumberOnScreen("8")
                }
                R.id.calculator_9 -> {
                    setNumberOnScreen("9")
                }
                R.id.calculator_clear -> {
                    clearScreen()
                }
                R.id.calculator_plus -> {
                    setSymbolOnScreen("+")
                }
                R.id.calculator_minus -> {
                    setSymbolOnScreen("-")
                }
                R.id.calculator_multiplication -> {
                    setSymbolOnScreen("*")
                }
                R.id.calculator_division -> {
                    setSymbolOnScreen("/")
                }
                R.id.calculator_square -> {
                    square()
                }
                R.id.calculator_square_root -> {
                    squareRoot()
                }
                R.id.calculator_dot -> {
                    setSymbolOnScreen(".")
                }
                R.id.calculator_sign -> {
                    changeSign()
                }
                R.id.calculator_equal -> {
                    calculateResult()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setNumberOnScreen(number: String) {
        if (calculatorScreen.text.toString() == "0") {
            calculatorScreen.text = number
        } else {
            calculatorScreen.text = calculatorScreen.text.toString() + number
        }
        isLastSymbol = false
    }

    @SuppressLint("SetTextI18n")
    private fun setSymbolOnScreen(symbol: String) {
        if (symbol == "." && !isDot) {
            isDot = true
            isLastSymbol = false
            calculatorScreen.text = calculatorScreen.text.toString() + symbol
        } else if (symbol != ".") {
            isResult = false
            isDot = false
            isLastSymbol = true
            calculatorScreen.text = calculatorScreen.text.toString() + symbol
        }
    }

    private fun changeSign() {
        if (isResult) {
         var result = calculatorScreen.text.toString().toDouble()
            result *= -1
            if (result % 1.0 == 0.0) {
                calculatorScreen.text = result.toInt().toString()
            } else {
                calculatorScreen.text = result.toString()
            }
        }
    }

    private fun clearScreen() {
        calculatorScreen.text = "0"
        isResult = true
        isDot = false
        isLastSymbol = false
    }

    private fun squareRoot() {
        var result = calculatorScreen.text.toString().toDouble()
        if (isResult && result > 0) {
            result = sqrt(result)
            calculatorScreen.text = result.toString()
        }
    }

    private fun square() {
        if (isResult) {
            var result = calculatorScreen.text.toString().toDouble()
            result *= result
            calculatorScreen.text = result.toString()
        }
    }

    private fun calculateResult() {

        var screen = calculatorScreen.text.toString().replace("--","-~").replace("+-","+~").replace("/-","/~").replace("*-","*~")
        Log.e("USER_LOG", screen.toString())
        if (screen.startsWith("-")) {
            screen = screen.removePrefix("-")
            screen = "~$screen"
            }
        Log.i("USER_LOG", screen.toString())
        val numbers = screen.split("+", "-", "*", "/").filter { it.isNotEmpty() }.toMutableList()
        val operators = screen.split(Regex("[~0-9.]+")).filter { it.isNotEmpty() }.toMutableList()
        var result = 0.0
        var index = 0

        Log.e("USER_LOG", screen.toString())
        for (i in numbers.indices) {
            numbers[i] = numbers[i].replace("~", "-")
        }
        Log.i("USER_LOG", screen.toString())

        Log.i("USER_LOG", numbers.toString())
        Log.i("USER_LOG", operators.toString())

        while (index != -1) {
            index = operators.indexOfFirst { it in setOf("*", "/") }
            if (index != -1) {
                val number1 = numbers[index].toDouble()
                val number2 = numbers[index + 1].toDouble()
                result = if (operators[index] == "*") {
                    number1 * number2
                } else {
                    number1 / number2
                }
                operators.removeAt(index)
                numbers.removeAt(index)
                numbers[index] = result.toString()
            }
            Log.i("USER_LOG", result.toString())
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
            Log.i("USER_LOG", result.toString())
        }
        clearScreen()

        if (result % 1.0 == 0.0) {
            calculatorScreen.text = result.toInt().toString()
        } else {
            calculatorScreen.text = result.toString()
        }
    }
}