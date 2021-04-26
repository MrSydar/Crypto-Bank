package com.example.cryptobank.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.adapters.FavoriteCurrencyAdapter
import com.example.cryptobank.adapters.RemoteCurrencyAdapter
import com.example.cryptobank.database.DBHelper
import com.example.cryptobank.datamodel.Currency
import com.google.firebase.firestore.FirebaseFirestore

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

        loadRemoteValues()
    }


    private fun loadRemoteValues() {
        val favoriteCurrencies: MutableList<Currency> =
            ArrayList()
        val fdb = FirebaseFirestore.getInstance()
        fdb.document("users/$loggedUser").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val doc = task.result
                if (doc!!.exists()) {
                    val currencyVal: ArrayList<HashMap<String, String>>? =
                        doc.get("currencies") as ArrayList<HashMap<String, String>>?
                    println("before")
                    println(currencyVal)
                    currencyVal?.let {
                        for (elem in currencyVal) {
                            favoriteCurrencies.add(
                                Currency(
                                    elem.get("currency")!!,
                                    elem.get("price")!!
                                )
                            )
                        }
                    }


                    val favoriteCurrencyRecycler =
                        findViewById<RecyclerView>(R.id.currency_recycler)
                    favoriteCurrencyRecycler.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@AccountActivity, 1)
                        adapter =
                            RemoteCurrencyAdapter(
                                favoriteCurrencies as MutableList<Currency>,
                                this@AccountActivity
                            )
                    }
                }
            } else {
                Toast.makeText(applicationContext, "Connection error", Toast.LENGTH_SHORT).show()
            }

            enableUI(true)
        }


    }

    private fun loadFavoriteLocal() {
        val dbHelper = DBHelper(this)

        val favoriteCurrencyRecycler = findViewById<RecyclerView>(R.id.favorite_currency_recycler)
        val favoriteCurrencies: MutableList<Currency>? = loggedUser?.let {
            dbHelper.getFavoriteCurrencies(
                it
            )
        }

        favoriteCurrencies?.let {
            println(favoriteCurrencies)
            favoriteCurrencyRecycler.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@AccountActivity, 1)
                adapter =
                    loggedUser?.let { it1 ->
                        FavoriteCurrencyAdapter(
                            favoriteCurrencies as MutableList<Currency>,
                            this@AccountActivity, it1
                        )
                    }
            }
        }

    }
}