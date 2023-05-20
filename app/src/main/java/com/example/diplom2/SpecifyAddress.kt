package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SpecifyAddress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specify_address)

        val price = intent.getStringExtra("price")!!

        val submitAddressButton = findViewById<Button>(R.id.submitAddressButton)

        submitAddressButton.setOnClickListener {
            val editTextAddress = findViewById<EditText>(R.id.editTextAddress)

            val address = editTextAddress.text.toString()

            val intent = Intent(this, Checkout::class.java)
            intent.putExtra("price", price)
            intent.putExtra("address", address)
            intent.putExtra("type", "home")

            startActivity(intent)
        }


    }
}