package com.example.habrfreelance.data_classes.task_full

import com.example.habrfreelance.data_classes.Tag

data class TaskFull(
    val author_avatar: String,
    val author_name: String,
    val description: String,
    val files: List<File>,
    val id: List<Int>,
    val price: Int,
    val published_at: String,
    val responses: Int,
    val tags: List<Tag>,
    val title: String,
    val url: String,
    val views: Int
)