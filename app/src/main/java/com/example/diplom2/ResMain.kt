package com.example.diplom2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class ResMain : AppCompatActivity() {

    private lateinit var categoryListView: ListView

    private val categories = arrayOf(
        "Новинки",
        "Бургеры",
        "Супы",
        "Салаты",
        "Десерты"
    )

    private lateinit var type :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_main)

        type = intent.getStringExtra("type")!!
        val tableName = intent.getStringExtra("table number")
        val tableNameTextView= findViewById<TextView>(R.id.table_name)

        if (type == "home")
            tableNameTextView.text = "Доставка на дом"
        else
            tableNameTextView.text = "Ваш столик под номером $tableName"

        categoryListView = findViewById(R.id.categoryListView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categories)
        categoryListView.adapter = adapter

        categoryListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val category = parent.getItemAtPosition(position) as String
//            Toast.makeText(this, "Выбрана категория: $category", Toast.LENGTH_SHORT).show()
            // Здесь можно добавить код для перехода на экран с блюдами выбранной категории

            if (category === "Новинки") {
                val intent = Intent(this, Menu::class.java)
                intent.putExtra("category", "new-products")
                startActivityForResult(intent, 0)
            }

        }

    }

    private var fullPrice= 0



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
                val name = data?.getStringExtra("name")
                val price = data?.getStringExtra("price")!!.toInt()
                val cartItems = mutableListOf<String>()

                fullPrice += price


                val cartListView = findViewById<ListView>(R.id.cartListView)
                val checkoutButton = findViewById<Button>(R.id.checkoutButton)

                val cartAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cartItems)

                cartListView.adapter = cartAdapter

                val itemToAdd = "Заказ на $fullPrice₽"
                cartItems.add(itemToAdd)
                cartAdapter.notifyDataSetChanged()

                checkoutButton.setOnClickListener {
                    cartItems.clear()
                    cartAdapter.notifyDataSetChanged()

                    if (type == "home") {
                        val intent = Intent(this, SpecifyAddress::class.java)
                        intent.putExtra("price", fullPrice.toString())
                        startActivity(intent)

                    }
                    else
                    {
                        val intent = Intent(this, Checkout::class.java)
                        intent.putExtra("price", fullPrice.toString())

                        startActivity(intent)
                    }


                }


        }
    }


}