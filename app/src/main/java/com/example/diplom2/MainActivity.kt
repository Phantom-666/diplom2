package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.yandex.mapkit.MapKitFactory

//TODO: Проложить путь
//TODO: Draw Icon for restaurant
//TODO: Check scan
//TODO: Menu

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MapKitFactory.setApiKey("22f07aaf-73dd-49eb-8908-b44fa12347c4")

        val mapButton = findViewById<Button>(R.id.map_button)

        mapButton.setOnClickListener {
            val intent = Intent(this, Map::class.java)
            startActivity(intent)
        }

        val qrCodeButton = findViewById<Button>(R.id.qr_code_button)

        qrCodeButton.setOnClickListener {
            val intent = Intent(this, QrCode::class.java)
            startActivity(intent)
        }

        val homeDelivery = findViewById<Button>(R.id.home_delivery)

        homeDelivery.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("type", "home")
            startActivity(intent)
        }

    }
}