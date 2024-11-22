package com.example.xml_app.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.xml_app.MainActivity
import com.example.xml_app.R
import com.example.xml_app.viewModel.BottomBarViewModel
import com.example.xml_app.viewModel.CalculatorViewModel
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

    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[CalculatorViewModel::class.java]
        Log.i("USER_LOG1",viewModel.calculatorScreenInfo.value.screen)
    }
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
        calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
        Log.i("USER_LOG2",viewModel.calculatorScreenInfo.value.screen)
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
                    viewModel.setNumberOnScreen("0")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_1 -> {
                    viewModel.setNumberOnScreen("1")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_2 -> {
                    viewModel.setNumberOnScreen("2")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_3 -> {
                    viewModel.setNumberOnScreen("3")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_4 -> {
                    viewModel.setNumberOnScreen("4")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_5 -> {
                    viewModel.setNumberOnScreen("5")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_6 -> {
                    viewModel.setNumberOnScreen("6")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_7 -> {
                    viewModel.setNumberOnScreen("7")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_8 -> {
                    viewModel.setNumberOnScreen("8")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_9 -> {
                    viewModel.setNumberOnScreen("9")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_clear -> {
                    viewModel.clearScreen()
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_plus -> {
                    viewModel.setSymbolOnScreen("+")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_minus -> {
                    viewModel.setSymbolOnScreen("-")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_multiplication -> {
                    viewModel.setSymbolOnScreen("*")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_division -> {
                    viewModel.setSymbolOnScreen("/")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_square -> {
                    viewModel.square()
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_square_root -> {
                    viewModel.squareRoot()
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_dot -> {
                    viewModel.setSymbolOnScreen(".")
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_sign -> {
                    viewModel.changeSign()
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
                R.id.calculator_equal -> {
                    viewModel.calculateResult()
                    calculatorScreen.text = viewModel.calculatorScreenInfo.value.screen
                }
            }
        }
    }
}