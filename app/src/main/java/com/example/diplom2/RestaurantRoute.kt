package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class RestaurantRoute : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_route)

        val homeDeliveryButton = findViewById<Button>(R.id.home_delivery_res)

        homeDeliveryButton.setOnClickListener {
            val intent = Intent(this, ResMain::class.java)
            intent.putExtra("type", "home")
            startActivity(intent)
        }
    }
}