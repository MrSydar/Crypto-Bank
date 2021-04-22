package com.example.cryptobank.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.adapters.CurrencyAdapter
import com.example.cryptobank.adapters.FavoriteCurrencyAdapter
import com.example.cryptobank.database.DBHelper
import com.example.cryptobank.datamodel.Currency

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

        loadFavoriteLocal()


    }

    private fun loadFavoriteLocal() {
        val dbHelper = DBHelper(this)

        val userRecyler = findViewById<RecyclerView>(R.id.favorite_currency_recycler)
        val favoriteCurrencies: MutableList<Currency>? = dbHelper.getFavoriteCurrencies()

        favoriteCurrencies?.let {

            println(favoriteCurrencies)
            userRecyler.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@KontoActivity, 1)
                adapter =
                    FavoriteCurrencyAdapter(
                        favoriteCurrencies as MutableList<Currency>,
                        this@KontoActivity
                    )
            }
        }

    }
}