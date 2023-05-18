package com.example.diplom2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val type = intent.getStringExtra("type")

        val tableName = intent.getStringExtra("table number")
        val tableNameTextView= findViewById<TextView>(R.id.table_name)

        if (type == "home")
            tableNameTextView.text = "Доставка на дом"
        else
            tableNameTextView.text = "Ваш столик под номером $tableName"

    }
}