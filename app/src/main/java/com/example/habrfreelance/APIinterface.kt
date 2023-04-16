package com.example.habrfreelance

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIinterface {
    @GET("/list")
    suspend  fun getTasks(): task

    @GET("/task/{id}")
    suspend fun getTaskById(@Path("id") id: Int) : task
}