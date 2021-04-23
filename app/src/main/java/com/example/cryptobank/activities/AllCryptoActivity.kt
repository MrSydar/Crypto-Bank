package com.example.cryptobank.activities

import android.content.DialogInterface
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptobank.R
import com.example.cryptobank.adapters.CurrencyAdapter
import com.example.cryptobank.database.DBHelper
import com.example.cryptobank.datamodel.Currency
import com.example.cryptobank.services.ICryptoCurrencyService
import com.example.cryptobank.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllCryptoActivity : ChangeableActivity() {

    var loggedUser: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_crypto)

        val backButtonView = findViewById<ImageView>(R.id.back_to_account)
        loggedUser = intent.extras!!.getString("userIN")
        loggedUser?.let {
            println("all crypto $loggedUser")


        }

        backButtonView.setOnClickListener {

            changeActivity(AccountActivity::class, loggedUser)
        }
        loadCurrencies()

    }


    private fun loadCurrencies() {
        //initiate the service
        val destinationService = ServiceBuilder.buildService(ICryptoCurrencyService::class.java)
        val requestCall = destinationService.getAll()
        val userRecyler = findViewById<RecyclerView>(R.id.user_recycler)
        //make network call asynchronously
        requestCall.enqueue(object : Callback<List<Currency>> {
            override fun onResponse(
                call: Call<List<Currency>>,
                response: Response<List<Currency>>
            ) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val usersList = response.body()!!
                    Log.d("Response", "countrylist size :")

                    userRecyler.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@AllCryptoActivity, 1)
                        adapter =
                            loggedUser?.let {
                                CurrencyAdapter(
                                    usersList as MutableList<Currency>,
                                    this@AllCryptoActivity, it
                                )
                            }
                    }
                } else {
                    Toast.makeText(
                        this@AllCryptoActivity,
                        "Something went wrong ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Currency>>, t: Throwable) {
                Toast.makeText(
                    this@AllCryptoActivity,
                    "Something went wrong $t",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        })
    }
}
