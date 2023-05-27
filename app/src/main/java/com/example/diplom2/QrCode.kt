package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator

@Suppress("DEPRECATION")
class QrCode : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_code)

        val scanQrCode = findViewById<Button>(R.id.scan_qr_code)
        scanQrCode.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.setPrompt("Scan a QR Code")
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.initiateScan()
        }


        val cannotScan = findViewById<TextView>(R.id.cannot_scan)
        cannotScan.setOnClickListener {
            val intent = Intent(this, CannotScan::class.java)
            startActivity(intent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (intentResult != null) {
            val contents = intentResult.contents
            if (contents != null) {


                var tableNumber = contents.replace("table number ", "").trim()

                val intent = Intent(this, ResMain::class.java)
                intent.putExtra("table number", tableNumber)
                intent.putExtra("type", "qrcode")
                startActivity(intent)
            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}