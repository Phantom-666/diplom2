package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class Checkout : AppCompatActivity() {
        private fun addToDatabase(cart : String, price : Int, type :String, location :String?) :String {

            var refName = "orders"

            if (type == "home") {
                refName += " home"
            }

            val firebase = FirebaseDatabase.getInstance().getReference(refName)
            val id = firebase.push().key!!

            if (type == "home") {
                val order = OrderHomeClass(id,
                    cart, price, location!!
                )

                firebase.child(id).setValue(order).addOnCompleteListener {
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            } else {

                val currentDate = Calendar.getInstance().time
                val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val formattedDate = dateFormatter.format(currentDate)

                val tableName = intent.getStringExtra("table number")!!

                val currentTime = Calendar.getInstance()
                val minutes = currentTime.get(Calendar.MINUTE)
                val hours = currentTime.get(Calendar.HOUR_OF_DAY)

                val formattedTime = String.format("%02d:%02d", hours, minutes)

                val order = OrderClass(id,
                    cart, price, tableName, formattedTime, formattedDate
                )

                firebase.child(id).setValue(order).addOnCompleteListener {
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

            }

            return id


    }


    private lateinit var dataStore: DataStore<Preferences>

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        withContext(Dispatchers.IO) {
            dataStore.edit { settings ->
                settings[dataStoreKey] = value
            }
        }
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        return withContext(Dispatchers.IO) {
            val preferences = dataStore.data.first()
            preferences[dataStoreKey]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        dataStore = createDataStore(name = "orders")

        val price = intent.getStringExtra("price")!!
        val cart = intent.getStringExtra("cart")!!
        val type = intent.getStringExtra("type")!!
        val address = intent.getStringExtra("address")

        val orderIdTextView = findViewById<TextView>(R.id.orderIdTextView)

        val goHomeButton = findViewById<Button>(R.id.goHomeButton)

        goHomeButton.visibility = View.INVISIBLE

        lifecycleScope.launch {
            val current = read("orders")

            if (current == null || current.trim() == "") {
                save("orders", "Заказ : $price₽")
            } else {
                save("orders", "Заказ : $price₽\n$current")
            }

            val id = addToDatabase(cart, price.toInt(), type, address)
            orderIdTextView.text = id

            goHomeButton.visibility = View.VISIBLE
        }

        goHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            intent.putExtra("new window", "no")
            startActivity(intent)
            finish()
        }
    }
}