package com.example.habrfreelance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habrfreelance.data_classes.task_min.TaskX

class TaskAdapter(val tasks: List<TaskX>, val tagOnClick: (String) -> Unit, itemOnClick: (Int) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title: TextView = item.findViewById(R.id.taskTitleTV)
        val responses: TextView = item.findViewById(R.id.taskResponsesTV)
        val views: TextView = item.findViewById(R.id.taskViewsTV)
        val published: TextView = item.findViewById(R.id.taskPublishedTV)
        val price: TextView = item.findViewById(R.id.taskPriceTV)
        val tags: RecyclerView = item.findViewById(R.id.taskTagsRV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myViewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return MyViewHolder(myViewHolder)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = tasks[position].title
        holder.responses.text = tasks[position].responses
        holder.views.text = tasks[position].views
        holder.published.text = tasks[position].published_at
        holder.price.text = tasks[position].price
        val adapter = TagAdapter(tasks[position].tags, tagOnClick)

        holder.tags.adapter = adapter
        holder.title.setOnClickListener {
            tasks[position].id
        }



    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}