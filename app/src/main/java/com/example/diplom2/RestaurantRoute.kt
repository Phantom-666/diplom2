package com.example.diplom2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class RestaurantRoute : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_route)


        val makeRoute = findViewById<Button>(R.id.make_route)


        makeRoute.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("action", "make_route")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        val homeDeliveryButton = findViewById<Button>(R.id.home_delivery_res)

        homeDeliveryButton.setOnClickListener {
            val intent = Intent(this, ResMain::class.java)
            intent.putExtra("type", "home")
            startActivity(intent)
        }
    }


}