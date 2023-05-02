package com.example.habrfreelance

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.retrofit.RetrofitObj
import com.google.android.flexbox.FlexboxLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TaskDesc : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_desc)
        Toast.makeText(this, intent.getIntExtra("ID",0).toString(), Toast.LENGTH_SHORT).show()
        GlobalScope.launch {
            try {
                val task = RetrofitObj.retrofit.create(APIinterface::class.java).getTaskById(intent.getIntExtra("ID",0))
                runOnUiThread {
                    val titleTV : TextView = findViewById(R.id.taskDescTitleTV)
                    val descTV : TextView = findViewById(R.id.taskDescDescTV)
                    val priceTV : TextView = findViewById(R.id.taskDescPriceTV)
                    val publishedTV : TextView = findViewById(R.id.taskDescPublishedTV)
                    val responsesTV : TextView = findViewById(R.id.taskDescResponsesTV)
                    val viewsTV : TextView = findViewById(R.id.taskDescViewsTV)
                    val tagsFlL : FlexboxLayout = findViewById(R.id.taskDescTagsFlL)
                    val filesIV : ImageView = findViewById(R.id.taskDescFilesIV)
                    val filesTV : ImageView = findViewById(R.id.taskDescFilesIV)
                    val filesLL : LinearLayout = findViewById(R.id.taskDescFilesLL)
                    val respondBtn : Button = findViewById(R.id.taskDescRespondBtn)

                    titleTV.text = task.title
                    descTV.text = task.description
                    if (task.price == -1) {
                        priceTV.text = "Цена договорная"
                    } else {
                        priceTV.text = task.price.toString() + " руб. за проект"
                    }
                    publishedTV.text = task.published_at
                    responsesTV.text = task.responses.toString()
                    viewsTV.text = task.views.toString()
                    if (task.files.isEmpty()) {
                        filesIV.visibility = View.INVISIBLE
                        filesTV.visibility = View.INVISIBLE
                        filesLL.visibility = View.INVISIBLE
                    } else {
                        for (file in task.files) {
                            val inflater = LayoutInflater.from(this@TaskDesc)
                            val fileL = inflater.inflate(R.layout.file_layout, null)
                            fileL.findViewById<TextView>(R.id.fileNameTV).text = file.title
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(file.url))
                            val chooserIntent = Intent.createChooser(browserIntent, "Choose a browser")
                            startActivity(chooserIntent)
                        }
                    }
                    for (tag in task.tags) {
                        val inflater = LayoutInflater.from(this@TaskDesc)
                        val tagL = inflater.inflate(R.layout.tag_layout, null)
                        tagL.findViewById<TextView>(R.id.tagTV).text = tag.title
                        tagsFlL.addView(tagL)
                    }

                    respondBtn.setOnClickListener {
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(task.url))
                        val chooserIntent = Intent.createChooser(browserIntent, "Choose a browser")
                        startActivity(chooserIntent)
                    }

                }

            } catch (e: Exception) {
                Log.e("Error", e.toString() )
            }
        }
    }
}