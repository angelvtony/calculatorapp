package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false
    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onAllcLearClick(view: View) {
        binding.dataTv.text=""
        binding.resultsTv.text=""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.resultsTv.visibility = View.GONE

    }


    fun onEqualClick(view: View) {
        onEqual()
        binding.dataTv.text = binding.resultsTv.text.toString().drop(1)
    }


    fun onDigitClick(view: View) {
         if (stateError){
             binding.dataTv.text = (view as Button).text
             stateError = false
         }else{
             binding.dataTv.append((view as Button).text)
         }
        lastNumeric = true
        onEqual()
    }


    fun onOperatorClick(view: View) {
        if (!stateError && lastNumeric){
            binding.dataTv.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            onEqual()
        }
    }


    fun onClearClick(view: View) {
        binding.dataTv.text = ""
        lastNumeric = false
    }


    fun onBackClick(view: View) {
        binding.dataTv.text = binding.dataTv.text.toString().dropLast(1)
        try {
            val lastChar = binding.dataTv.text.toString().last()
            if (lastChar.isDigit()){
                onEqual()
            }
        }catch (e:Exception){
            binding.resultsTv.text = ""
            binding.resultsTv.visibility = View.GONE
            Log.d("Last Character",e.toString())
        }
    }

    fun onEqual(){
        if (lastNumeric && !stateError){
            val txt = binding.dataTv.text.toString()
            expression = ExpressionBuilder(txt).build()
            try {
                val result = expression.evaluate()
                binding.resultsTv.visibility = View.VISIBLE
                binding.resultsTv.text = "=" + result.toString()
            }catch (ex:ArithmeticException){
                Log.d("Evaluate Error",ex.toString())
                binding.resultsTv.text = "Error"

                stateError = true
                lastNumeric = false
            }
        }
    }




}