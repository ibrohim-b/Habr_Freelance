package com.example.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObj {
    val retrofit = Retrofit.Builder().baseUrl("http://127.0.0.1:8000")
        .addConverterFactory(GsonConverterFactory.create()).build()
}