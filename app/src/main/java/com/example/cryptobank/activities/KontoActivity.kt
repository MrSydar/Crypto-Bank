package com.example.cryptobank.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.cryptobank.R

class KontoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_konto)
        val crypto = findViewById<Button>(R.id.crypto)
        crypto.setOnClickListener {
            Thread() {
                run {
                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, AllCryptoActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        }
    }
}