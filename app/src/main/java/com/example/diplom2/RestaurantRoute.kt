package com.example.diplom2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class RestaurantRoute : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_route)

        val makeRoute = findViewById<Button>(R.id.make_route)
        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")

        makeRoute.setOnClickListener {
            val resultIntent = Intent()

            resultIntent.putExtra("action", "make_route")

            resultIntent.putExtra("latitude", latitude)
            resultIntent.putExtra("longitude", longitude)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        val homeDeliveryButton = findViewById<Button>(R.id.home_delivery_res)
        homeDeliveryButton.setOnClickListener {
            val intent = Intent(this, ResMain::class.java)
            intent.putExtra("type", "home")
            startActivity(intent)
        }

        val reserveTableButton = findViewById<Button>(R.id.reserveTableButton)

        reserveTableButton.setOnClickListener{
            val intent = Intent(this, ReserveTable::class.java)
            startActivity(intent)
        }
    }


}