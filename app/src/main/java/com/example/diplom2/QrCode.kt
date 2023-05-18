package com.example.diplom2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlin.collections.Map

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

        val fakeScan = findViewById<Button>(R.id.fake_scan)
        fakeScan.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            intent.putExtra("table number", "1")
            intent.putExtra("type", "qrcode")
            startActivity(intent)
        }

        val cannotScan = findViewById<TextView>(R.id.cannot_scan)
        cannotScan.setOnClickListener {
            val intent = Intent(this, CannotScan::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        var intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (intentResult != null) {
            val contents = intentResult.contents
            if (contents != null) {
                var tableNumber = ""
                for (i in 0.. contents.length) {
                    if (i > 12) {
                        tableNumber += contents[i]
                    }
                }
                val intent = Intent(this, Menu::class.java)
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