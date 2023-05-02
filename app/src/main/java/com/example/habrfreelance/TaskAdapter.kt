package com.example.habrfreelance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.example.habrfreelance.data_classes.TaskX
import com.example.habrfreelance.data_classes.task
import com.google.android.flexbox.FlexboxLayout

class TaskAdapter(val tasks: List<TaskX>, val tagOnClick: (String) -> Unit,var itemOnClick: (Int) -> Unit,var loadMoreOnClick: (String) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val title: TextView? = item.findViewById(R.id.taskTitleTV)
        val responses: TextView? = item.findViewById(R.id.taskResponsesTV)
        val views: TextView? = item.findViewById(R.id.taskViewsTV)
        val published: TextView? = item.findViewById(R.id.taskPublishedTV)
        val price: TextView? = item.findViewById(R.id.taskPriceTV)
//        val tags: RecyclerView? = item.findViewById(R.id.taskTagsRV)
        val tags: FlexboxLayout? = item.findViewById(R.id.taskTagsFlL)
        val priceExtra :TextView? = item.findViewById(R.id.taskPriceExtraTV)
        val priceStatus :TextView? = item.findViewById(R.id.taskPriceStatusTV)
        val loadMore :TextView? = item.findViewById(R.id.loadMore)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        if (viewType == 1) {
            val myViewHolder =
                LayoutInflater.from(parent.context).inflate(R.layout.task_layout, parent, false)
            return MyViewHolder(myViewHolder)
        } else {
            val myViewHolder =
                LayoutInflater.from(parent.context).inflate(R.layout.pagination_button, parent, false)
            return MyViewHolder(myViewHolder)
        }
    }
    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) {
            -1
        } else {
            1
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        try {
            holder.title!!.text = tasks[position].title
            holder.responses!!.text = tasks[position].responses
            holder.views!!.text = tasks[position].views
            if (tasks[position].price.toInt() == -1) {
                holder.price!!.text = "договорная"
                holder.priceExtra!!.text = ""
                holder.priceStatus!!.text = "≈"
            } else {
                holder.price!!.text = tasks[position].price
            }
            holder.published!!.text = tasks[position].published_at
//            val lp = FlexboxLayout.LayoutParams(
//                FlexboxLayout.LayoutParams.WRAP_CONTENT,
//                FlexboxLayout.LayoutParams.WRAP_CONTENT
//            )
            val lp = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(50, 50, 50, 50)

            for (tag in tasks[position].tags) {
                val inflater = LayoutInflater.from(holder.tags!!.context)
                val tagL = inflater.inflate(R.layout.tag_layout, null)
                if (holder.tags!!.childCount > 7) {
                    tagL.findViewById<TextView>(R.id.tagTV).text = "..."
                    break
                }
                tagL.findViewById<TextView>(R.id.tagTV).text = tag.title
                tagL.findViewById<TextView>(R.id.tagTV).layoutParams = lp
                tagL.setOnClickListener {
                    tagOnClick.invoke(tag.title)
                }
                holder.tags.addView(tagL)
            }
            holder.title.setOnClickListener {
                itemOnClick.invoke(tasks[position].id.toInt())
            }
        } catch (e:Exception) {
            holder.loadMore!!.setOnClickListener {
                loadMoreOnClick.invoke("LOAD MORE")
            }
        }
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}