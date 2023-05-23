package com.example.diplom2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class Orders : AppCompatActivity() {

    private lateinit var  dataStore : DataStore<Preferences>
    private lateinit var ordersListView: ListView
    private lateinit var noOrdersTextView: TextView

    private suspend fun read(key : String) :String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()

        return preferences[dataStoreKey]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        dataStore = createDataStore(name = "orders")
        ordersListView = findViewById(R.id.ordersListView)
        noOrdersTextView = findViewById(R.id.noOrdersTextView)

        lifecycleScope.launch {
            val current = read("orders")

            val orders = mutableListOf<String>()
            if (current != null && current != "") {
                val split = current.split('\n')

                split.map {
                    orders.add(it)
                }
            }

            val adapter = ArrayAdapter(this@Orders, android.R.layout.simple_list_item_1, orders)
            ordersListView.adapter = adapter

            if (orders.isEmpty()) {
                ordersListView.visibility = View.GONE
                noOrdersTextView.visibility = View.VISIBLE
            }
        }
    }
}