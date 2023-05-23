package com.example.diplom2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

@Suppress("DEPRECATION")
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

        val robotDelivery = findViewById<LinearLayout>(R.id.robot_delivery)

        if (type == "home")
        {
            tableNameTextView.text = "Доставка на дом"
            robotDelivery.visibility = View.GONE
        }
        else {
            val tableNameTextViewText = "Ваш столик под номером $tableName"

            tableNameTextView.text = tableNameTextViewText
            robotDelivery.visibility = View.VISIBLE
        }

        categoryListView = findViewById(R.id.categoryListView)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categories)
        categoryListView.adapter = adapter

        categoryListView.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val category = parent.getItemAtPosition(position) as String
            val intent = Intent(this, Menu::class.java)

            if (category === "Новинки")
                intent.putExtra("category", "new-products")
            else if (category === "Бургеры")
                intent.putExtra("category", "burgers")
            else if (category === "Супы")
                intent.putExtra("category", "soups")
            else if (category === "Салаты")
                intent.putExtra("category", "salads")
            else if (category === "Десерты")
                intent.putExtra("category", "dessert")

            startActivityForResult(intent, 0)
        }


        val myCheckbox = findViewById<CheckBox>(R.id.checkboxOverlay)

        myCheckbox.setOnClickListener{
            val cartItems = mutableListOf<String>()
            val cartAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, cartItems)

            val cartListView = findViewById<ListView>(R.id.cartListView)
            cartListView.adapter = cartAdapter

            if (myCheckbox.isChecked) {
                // Checkbox is checked
                fullPrice += 300

            } else {
                // Checkbox is not checked
                fullPrice -= 300
            }

            val itemToAdd = "Заказ на $fullPrice₽"
            cartItems.add(itemToAdd)
            cartAdapter.notifyDataSetChanged()
        }
    }

    private var fullPrice= 0



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
//                val name = data?.getStringExtra("name")
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