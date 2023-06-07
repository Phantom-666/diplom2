package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {
//    private fun addToDatabase() {
//            val firebase = FirebaseDatabase.getInstance().getReference("locations")
//            val id = firebase.push().key!!
//
//            val product = LocationClass(id,
//                55.638079,
//                51.803866
//            )
//
//            firebase.child(id).setValue(product).addOnCompleteListener {
//            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
//        }.addOnFailureListener { err ->
//            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val newWindow = intent.getStringExtra("new window")

        if (newWindow == null) {
            MapKitFactory.setApiKey("22f07aaf-73dd-49eb-8908-b44fa12347c4")
            MapKitFactory.initialize(this)
        }

        setContentView(R.layout.activity_main)

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
            val intent = Intent(this, ResMain::class.java)
            intent.putExtra("type", "home")
            startActivity(intent)
        }

        val ordersTabButton = findViewById<Button>(R.id.ordersTabButton)
        ordersTabButton.setOnClickListener {
            val intent = Intent(this, Orders::class.java)
            startActivity(intent)
        }
    }
}

