package com.example.cryptobank.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.adapters.CurrencyAdapter
import com.example.cryptobank.datamodel.Currency
import com.example.cryptobank.services.ICryptoCurrencyService
import com.example.cryptobank.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_main)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)


        login.setOnClickListener() {
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
        register.setOnClickListener() {

            Thread() {
                run {

                    Thread.sleep(1000);
                }
                runOnUiThread() {
                    val intent = Intent(this, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        }
    }

}