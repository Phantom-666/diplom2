package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class ReserveName : AppCompatActivity() {

        private fun addToDatabase(name :String, tableName : String, time : String) {
            val firebase = FirebaseDatabase.getInstance().getReference("tables")
            val id = firebase.push().key!!

            val currentDate = Calendar.getInstance().time
            val dateFormatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val formattedDate = dateFormatter.format(currentDate)

            val t = TablesClass(id,name, tableName, time, formattedDate)

            firebase.child(id).setValue(t).addOnCompleteListener {
        }.addOnFailureListener { err ->
            Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_name)


        val table = intent.getStringExtra("table")!!


        val reserveNameNextButton = findViewById<Button>(R.id.ReserveNameNextButton)
        val editTextReserveName = findViewById<EditText>(R.id.editTextReserveName)

        val editTextReserveTime = findViewById<EditText>(R.id.editTextReserveTime)


        reserveNameNextButton.setOnClickListener{
            addToDatabase(editTextReserveName.text.toString(), table, editTextReserveTime.text.toString())

            val intent = Intent(this, ReserveFinish::class.java)
            startActivity(intent)
        }

    }
}