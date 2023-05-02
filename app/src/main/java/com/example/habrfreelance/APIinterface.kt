package com.example.habrfreelance

import com.example.habrfreelance.data_classes.TaskFull
import com.example.habrfreelance.data_classes.task
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIinterface {
    @GET("/list")
    suspend  fun getTasks(@Query("skip") skip: Int = 0): task

    @GET("/task/{id}")
    suspend fun getTaskById(@Path("id") id: Int) : TaskFull

}