package com.example.habrfreelance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TagAdapter(val tags: List<String>, val itemOnClick: (String) -> Unit) :
    RecyclerView.Adapter<TagAdapter.MyViewHolder>() {

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title: TextView = item.findViewById(R.id.tagTV)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val myViewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
        return MyViewHolder(myViewHolder)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = tags[position]
        holder.title.setOnClickListener {
            itemOnClick.invoke(tags[position])
        }

    }

    override fun getItemCount(): Int {
        return tags.size
    }
}