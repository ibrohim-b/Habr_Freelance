package com.example.habrfreelance.data_classes

data class TaskX(
    val id: String,
    val price: String,
    val published_at: String,
    val responses: String,
    val tags: List<Tag>,
    val title: String,
    val url: String,
    val views: String
)