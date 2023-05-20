package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class Checkout : AppCompatActivity() {

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
        setContentView(R.layout.activity_checkout)

        dataStore = createDataStore(name = "orders")


//        intent.putExtra("price", price)
//        intent.putExtra("address", address)
//        intent.putExtra("type", "home")

        val price = intent.getStringExtra("price")
        val type = intent.getStringExtra("type")

        val goHomeButton = findViewById<Button>(R.id.goHomeButton)

        goHomeButton.setOnClickListener {

            lifecycleScope.launch {

                val current = read("orders")

                if (current == null) {
                    save("orders", "Заказ : $price")
                } else {
                    save("orders", "Заказ : $price, $current")
                }
            }


            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }


    }
}