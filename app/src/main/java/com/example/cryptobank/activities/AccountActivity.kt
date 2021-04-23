package com.example.cryptobank.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.adapters.CurrencyAdapter
import com.example.cryptobank.adapters.FavoriteCurrencyAdapter
import com.example.cryptobank.database.DBHelper
import com.example.cryptobank.datamodel.Currency

class AccountActivity : ChangeableActivity() {
    var loggedUser: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        val crypto = findViewById<Button>(R.id.crypto)
        loggedUser = intent.extras!!.getString("userIN")
        val loggedUserView = findViewById<TextView>(R.id.loggedUser)
        loggedUserView.text = loggedUser
        crypto.setOnClickListener {
            changeActivity(AllCryptoActivity::class, loggedUser)
        }
        loadFavoriteLocal()
    }

    private fun loadFavoriteLocal() {
        val dbHelper = DBHelper(this)

        val userRecyler = findViewById<RecyclerView>(R.id.favorite_currency_recycler)
        val favoriteCurrencies: MutableList<Currency>? = loggedUser?.let {
            dbHelper.getFavoriteCurrencies(
                it
            )
        }

        favoriteCurrencies?.let {
            println(favoriteCurrencies)
            userRecyler.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@AccountActivity, 1)
                adapter =
                    FavoriteCurrencyAdapter(
                        favoriteCurrencies as MutableList<Currency>,
                        this@AccountActivity
                    )
            }
        }

    }
}