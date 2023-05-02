package com.example.habrfreelance

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Xml
import android.view.Gravity
import android.view.Menu
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.habrfreelance.data_classes.TaskX
import com.example.habrfreelance.data_classes.task
import com.example.retrofit.RetrofitObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var loadMorePressed : Int = 1
    val tasksPerPage : Int = 20
//    lateinit var tasksAll : ArrayList<TaskX>
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var tasksAll = ArrayList<TaskX>()
        val tasksRV : RecyclerView = findViewById(R.id.tasksRV)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        GlobalScope.launch() {
            try {
                val tasks = RetrofitObj.retrofit.create(APIinterface::class.java).getTasks()

                tasksAll.addAll(tasks.tasks)
    //            Log.e("Tasks", tasks.toString())
                runOnUiThread {
                    val adapter = TaskAdapter(tasksAll, itemOnClick = {
                        Toast.makeText(this@MainActivity, it.toString(), Toast.LENGTH_SHORT).show()
                        val intent =  Intent(this@MainActivity, TaskDesc::class.java)
                        intent.putExtra("ID", it)
                        startActivity(intent)
                    }, tagOnClick = {}, loadMoreOnClick = {
                        Toast.makeText(this@MainActivity, "LOAD", Toast.LENGTH_SHORT).show()
                        GlobalScope.launch {
                            Log.e("loadMorePressed", "$loadMorePressed times clicked")
                            try {
                                loadMorePressed += 1
                                val tasks = RetrofitObj.retrofit.create(APIinterface::class.java).getTasks(skip = loadMorePressed * tasksPerPage)
                                if (tasks.tasks.isEmpty()) {
                                    loadMorePressed -= 1
                                    Log.e("Tasks", "No TASKS", )
                                }
                                tasksAll.addAll(tasks.tasks)
                            } catch (e:java.lang.Exception) {
                                Log.e("Tasks", "FAILED TO LOAD MORE$e")
                                Toast.makeText(this@MainActivity, "FAILED TO LOAD MORE$e", Toast.LENGTH_SHORT).show()
                            } }
                    })
                    tasksRV.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
            }
            catch (e:java.lang.Exception) {
                Log.e("STATUS", "FAILED:$e.")
//                Toast.makeText(this@MainActivity, "RERTROFIT FAILED ${e.toString()}", Toast.LENGTH_SHORT).show()
                runOnUiThread {
                    val noInternetIV = ImageView(this@MainActivity)
                    noInternetIV.setImageResource(R.drawable.no_internet)
                    noInternetIV.setColorFilter(Color.parseColor("#A8C5CF"))
                    var layoutParams = LinearLayout.LayoutParams(500, 500)
                    layoutParams.gravity = Gravity.CENTER_HORIZONTAL
                    noInternetIV.layoutParams = layoutParams
                    noInternetIV.setOnClickListener {
                        recreate()
                    }
                    val noInternetTV = TextView(this@MainActivity)
                    noInternetTV.gravity = Gravity.CENTER
                    noInternetTV.text = "Нет или медленное подключение к Интернету \n Пожалуйста, проверьте настройки вашего Интернета"
                    findViewById<LinearLayout>(R.id.taskFails).addView(noInternetIV)
                    findViewById<LinearLayout>(R.id.taskFails).addView(noInternetTV)
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu?.findItem(R.id.taskSearchSV)
        val searchView = searchItem?.actionView as SearchView

        searchView.apply {
            maxWidth = Integer.MAX_VALUE
            isIconified = false
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    Log.e("SEARCH BAR", "onQueryTextSubmit: $query")
                    TODO("RETROFIT NAME FILTER REQUEST")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.e("SEARCH BAR", "onQueryTextChange: $newText")
                    return true
                }

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

}