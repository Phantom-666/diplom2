package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class CannotScan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cannot_scan)

        val tableNameInput = findViewById<EditText>(R.id.table_name_input)
        val submitTableNumber = findViewById<Button>(R.id.submit_table_number)

        submitTableNumber.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("table number", tableNameInput.text.toString())
            intent.putExtra("type", "qrcode")
            startActivity(intent)

        }
    }
}