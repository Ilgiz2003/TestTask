package com.example.testcasecft

import com.example.testcasecft.data_classes.BankCardInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("{id}")
    fun getInfo(@Path("id") id: String): Call<BankCardInfo>

    companion object {

        var BASE_URL = "https://lookup.binlist.net/"

        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}