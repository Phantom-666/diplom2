package com.example.diplom2

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlin.collections.Map

class ReserveTable : AppCompatActivity() {
    private var selectedTableLayout: LinearLayout? = null
    private var tableNumber : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_table)

        val table1Layout: LinearLayout = findViewById(R.id.table1Layout)
        val table2Layout: LinearLayout = findViewById(R.id.table2Layout)
        val table3Layout: LinearLayout = findViewById(R.id.table3Layout)
        val table4Layout: LinearLayout = findViewById(R.id.table4Layout)

        table1Layout.setOnClickListener { selectTable(table1Layout, 1) }
        table2Layout.setOnClickListener { selectTable(table2Layout, 2) }
        table3Layout.setOnClickListener { selectTable(table3Layout, 3) }
        table4Layout.setOnClickListener { selectTable(table4Layout, 4) }

        val selectTableButton: Button = findViewById(R.id.selectTableButton)
        selectTableButton.setOnClickListener { onTableSelected() }
    }

    private fun selectTable(tableLayout: LinearLayout, tableNumber: Int) {
        // Clear the previously selected table background
        selectedTableLayout?.setBackgroundColor(Color.TRANSPARENT)

        // Set the new selected table layout
        selectedTableLayout = tableLayout

        this.tableNumber = tableNumber

        // Set the green background color
        val greenColor = ContextCompat.getColor(this, R.color.green)
        selectedTableLayout?.setBackgroundColor(greenColor)
    }

    private fun onTableSelected() {
        if (selectedTableLayout != null) {
            val selectedTableTextView: TextView? = selectedTableLayout?.findViewById<TextView>(resources.getIdentifier("textViewTable$tableNumber", "id", packageName))
            val selectedTableName: String = selectedTableTextView?.text.toString()
            Toast.makeText(this, "Вы выбрали $selectedTableName", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, ReserveName::class.java)
            intent.putExtra("table", selectedTableName)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Пожалуйста, выберите столик", Toast.LENGTH_SHORT).show()
        }
    }
}