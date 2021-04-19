package com.example.cryptobank.services

import com.example.cryptobank.datamodel.Currency
import retrofit2.Call
import retrofit2.http.GET
import java.util.*

interface ICryptoCurrencyService {

    @GET("prices?key=945bf0ab43d20e560a7361808dddd86a")
    fun getAll(): Call<List<Currency>>
}
