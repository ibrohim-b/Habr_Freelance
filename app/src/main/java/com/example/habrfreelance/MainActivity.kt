package com.example.habrfreelance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tasksRV : RecyclerView = findViewById(R.id.tasksRV)
        val adapter = TaskAdapter(emptyList(), itemOnClick = {}, tagOnClick = {})
        tasksRV.adapter = adapter

    }
}