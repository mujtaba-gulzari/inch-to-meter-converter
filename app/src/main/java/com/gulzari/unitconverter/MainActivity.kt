package com.gulzari.unitconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {

    private lateinit var inputEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var resultText: TextView
    private lateinit var clearButton: Button
    private lateinit var historyList: ListView
    private val historyItems = mutableListOf<String>()  //store history items
    var index: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        findViews()
        calculateButtonClickListener()
        clearButtonClickListener()

        //initialize history list adapter
        val historyAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, historyItems)
        historyList.adapter = historyAdapter
    }

    private fun calculateButtonClickListener() {
        calculateButton.setOnClickListener{
            val inputString = inputEditText.text.toString()
            if(inputString.isEmpty()){
                Toast.makeText(this@MainActivity, "You have not input any value. Please enter a value in inches!", Toast.LENGTH_LONG).show()
            } else {
                val result = convertToMeters(inputString)
                displayResult(result)
                addToHistory(inputString, result)
            }
        }
    }

    private fun clearButtonClickListener() {
        clearButton.setOnClickListener{
            val inputString = inputEditText.text.toString()
            if(inputString.isEmpty()){
                Toast.makeText(this@MainActivity, "You have not input any value. Please enter a value in inches!", Toast.LENGTH_LONG).show()
            } else {
                inputEditText.text.clear()
                resultText.text = ""

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun displayResult(result: Double) {
        val resultString = result.toString()
        val myDecimalFormatter = DecimalFormat("0.00")
        val formattedResultText = myDecimalFormatter.format(result)
        resultText.text = "$formattedResultText meters"
    }

    private fun addToHistory(input: String, result: Double) {
        index ++
        val formattedResult = DecimalFormat("0.00").format(result)
        val historyItem = "$formattedResult meters"
        historyItems.add(0, historyItem)
        //notify the adapter that the data set has changed
        (historyList.adapter as ArrayAdapter<*>).notifyDataSetChanged()
    }

    private fun findViews() {
        inputEditText = findViewById(R.id.input_edit_text)
        calculateButton = findViewById(R.id.calculate_button)
        resultText = findViewById(R.id.result_text_view)
        clearButton = findViewById(R.id.clear_button)
        historyList = findViewById(R.id.history_list_view)
    }

    private fun convertToMeters(inputString: String): Double {
        val input = Integer.parseInt(inputString)
        return  input / 39.37
    }


}