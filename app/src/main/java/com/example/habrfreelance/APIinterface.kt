package com.example.habrfreelance

import com.example.habrfreelance.data_classes.task
import retrofit2.http.GET
import retrofit2.http.Path

interface APIinterface {
    @GET("/list")
    suspend  fun getTasks(): task

    @GET("/task/{id}")
    suspend fun getTaskById(@Path("id") id: Int) : task
}