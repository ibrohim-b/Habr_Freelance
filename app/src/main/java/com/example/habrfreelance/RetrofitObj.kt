package com.example.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObj {
    val retrofit = Retrofit.Builder().baseUrl("http://192.168.100.6:8000")
        .addConverterFactory(GsonConverterFactory.create()).build()
}