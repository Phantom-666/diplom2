package com.example.diplom2

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class Cart : AppCompatActivity() {

    private fun Int.dpToPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)


        val cart = intent.getStringExtra("cart")!!.split(", ")
        val prices = intent.getStringExtra("prices")!!.split(", ")

        val cartLayout: LinearLayout = findViewById(R.id.cartLayout)


        for (index in cart.indices) {

            val itemLayout = createProduct(cart[index], prices[index].toInt())

            cartLayout.addView(itemLayout)
        }


    }


    private fun createProduct(name :String, price : Int) :View {
        // Создание нового элемента корзины
        val itemLayout = LinearLayout(this)
        itemLayout.orientation = LinearLayout.HORIZONTAL
        itemLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

// Создание TextView для имени товара
        val nameTextView = TextView(this)
        nameTextView.layoutParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            1.0f
        )
        nameTextView.text = name
        nameTextView.textSize = 18f
        nameTextView.setTypeface(null, Typeface.BOLD)
        itemLayout.addView(nameTextView)

// Создание TextView для цены товара
        val priceTextView = TextView(this)
        val priceLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        priceLayoutParams.gravity = Gravity.CENTER_VERTICAL
        priceLayoutParams.marginEnd = 16.dpToPx() // Конвертирование dp в пиксели
        priceTextView.layoutParams = priceLayoutParams
        priceTextView.text = "Цена: ${price}₽"
        priceTextView.textSize = 18f
        priceTextView.setTypeface(null, Typeface.BOLD)
        itemLayout.addView(priceTextView)

// Создание кнопки "Удалить"
        val deleteButton = Button(this)
        deleteButton.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        deleteButton.text = "Удалить"
        itemLayout.addView(deleteButton)


        deleteButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("name", name)
            resultIntent.putExtra("price", price.toString())
            resultIntent.putExtra("action", "delete")

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        return itemLayout

    }
}