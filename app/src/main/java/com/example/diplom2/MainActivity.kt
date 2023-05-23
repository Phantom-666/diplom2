package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.yandex.mapkit.MapKitFactory

//TODO: Проложить путь
//TODO: Check scan
//TODO: Add loader

class MainActivity : AppCompatActivity() {
//    private fun addToDatabase() {
        //        val firebase = FirebaseDatabase.getInstance().getReference("dessert")
//        val id = firebase.push().key!!
//
//        val product = ProductClass(id,
//            "Мороженое Карамельное",
//            84 ,
//            "https://vkusnoitochka.ru/resize/290x286/upload/iblock/25c/fib56dzowg2qt8sr6phpqv8arhabjfl1/large.png",
//            "Мороженое из натурального цельного молока с добавкой карамельного наполнителя")
//
//        firebase.child(id).setValue(product).addOnCompleteListener {
//            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
//        }.addOnFailureListener { err ->
//            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
//        }

//    }

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

