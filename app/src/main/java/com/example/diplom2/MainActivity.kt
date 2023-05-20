package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

//TODO: Проложить путь
//TODO: Draw Icon for restaurant
//TODO: Check scan
//TODO: Заказы
//TODO: add about and images for products
//TODO: create categories for db

class MainActivity : AppCompatActivity() {

    private lateinit var  dataStore : DataStore<Preferences>


    private suspend fun save(key : String, value : String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    private suspend fun read(key : String) :String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()

        return preferences[dataStoreKey]

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataStore = createDataStore(name = "orders")


        lifecycleScope.launch {
            val current = read("orders")

//            Toast.makeText(this, current, Toast.LENGTH_LONG).show()

            Log.d("work", current ?: "got nothing")

        }



        MapKitFactory.setApiKey("22f07aaf-73dd-49eb-8908-b44fa12347c4")

        val mapButton = findViewById<Button>(R.id.map_button)

//        val firebase = FirebaseDatabase.getInstance().getReference("new-products")
//        val id = firebase.push().key!!
//        val product = ProductClass(id, "Добрый Кола Апельсин", 95)
//        firebase.child(id).setValue(product).addOnCompleteListener {
//            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
//        }.addOnFailureListener { err ->
//            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
//        }

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



    }
}

