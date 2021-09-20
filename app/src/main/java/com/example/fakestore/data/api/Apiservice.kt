package com.example.fakestore.data.api

import com.example.fakestore.data.Data
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

var BASE_URL = "https://fakestoreapi.com/"

interface Apiservice {
    @GET("products")
    fun getItem(): Call<ArrayList<Data>>

    companion object {
        operator fun invoke(): Apiservice {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Apiservice::class.java)
        }
    }
}