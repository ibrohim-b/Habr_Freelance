package com.example.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//http://192.168.99.170:8000 ITRUN
//http://192.168.100.6:8000 HOME

object RetrofitObj {
    val retrofit = Retrofit.Builder().baseUrl("http://192.168.100.6:8000")
        .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
}
//telefonba zapusk shud lekin khichiyam nagirift
// server kor kadosmi?
// haminkhlekmi? ха дуруст акнун запуск
//kor nakard lekin zapusk shud
private val okHttpClient = OkHttpClient().newBuilder()
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()