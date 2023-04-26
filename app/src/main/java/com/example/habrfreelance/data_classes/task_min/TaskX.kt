package com.example.habrfreelance.data_classes.task_min

data class TaskX(
    val id: String,
    val price: String,
    val published_at: String,
    val responses: String,
    val tags: List<String>,
    val title: String,
    val url: String,
    val views: String
)