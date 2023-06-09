package com.example.diplom2

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import coil.load
import com.google.firebase.database.*

class Menu : AppCompatActivity() {

    private fun Int.dpToPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

    private fun parse(obj_str :String) : ProductClass {
        val result = obj_str.replace("{", "").replace("}", "").split(", ")

        var price: Int = -1
        var name = ""
        var id = ""
        var image = ""
        var about = ""

        for (c in result) {

            if (c.contains("price")) {
                price = c.replace("price=", "").toInt()
            } else if (c.contains("name")) {
                name = c.replace("name=", "")
            }
            else if (c.contains("id")) {
                id = c.replace("id=", "")
            }
            else if (c.contains("image")) {
                image = c.replace("image=", "")
            }
            else if (c.contains("about")) {
                about = c.replace("about=", "")
            }


        }

        return ProductClass(id, name, price, image, about)
    }

    private fun addBlock(name :String, price : Number, image :String, about : String) {

        val mainLayout = findViewById<LinearLayout>(R.id.appetizerLayout)
        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.HORIZONTAL

        linearLayout.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("name", name)
            resultIntent.putExtra("price", price.toString())
            resultIntent.putExtra("action", "add")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        linearLayout.layoutParams = layoutParams

        val imageView = ImageView(this)
        imageView.load(image)

        val imageParams = LinearLayout.LayoutParams(
            100.dpToPx(),
            100.dpToPx()
        )
        imageView.layoutParams = imageParams
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        val innerLinearLayout = LinearLayout(this)
        innerLinearLayout.orientation = LinearLayout.VERTICAL
        innerLinearLayout.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        val nameTextView = TextView(this)
        nameTextView.text = name
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        nameTextView.setTypeface(null, Typeface.BOLD)
        nameTextView.setPadding(16.dpToPx(), 8.dpToPx(), 0, 0)

        val descriptionTextView = TextView(this)
        descriptionTextView.text = about
        descriptionTextView.setPadding(16.dpToPx(), 4.dpToPx(), 16.dpToPx(), 8.dpToPx())

        val priceTextView = TextView(this)

        val priceTextViewText = "$price₽"
        priceTextView.text = priceTextViewText
        priceTextView.setTypeface(null, Typeface.BOLD)
        priceTextView.setPadding(16.dpToPx(), 0, 0, 8.dpToPx())

        innerLinearLayout.addView(nameTextView)
        innerLinearLayout.addView(descriptionTextView)
        innerLinearLayout.addView(priceTextView)

        linearLayout.addView(imageView)
        linearLayout.addView(innerLinearLayout)

        mainLayout.addView(linearLayout)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val category = intent.getStringExtra("category")
        val firebase = FirebaseDatabase.getInstance().getReference(category!!)


        val menuProgressBar = findViewById<ProgressBar>(R.id.menuProgressBar)

        firebase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists() && snapshot.hasChildren()) {
                    for (product in snapshot.children) {
                        val p = parse(product.value.toString())
                        addBlock(p.name, p.price, p.image, p.about)
                    }

                    menuProgressBar.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Marker1", "Not implemented")
            }
        })
    }
}