package com.example.habrfreelance

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
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

        Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show()

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
                    }, tagOnClick = {}, loadMoreOnClick = {
                        Toast.makeText(this@MainActivity, "LOAD", Toast.LENGTH_SHORT).show()
                        GlobalScope.launch {
                            loadMorePressed += 1
                            Log.e("loadMorePressed", "$loadMorePressed times clicked")
                            try {
                                val tasks = RetrofitObj.retrofit.create(APIinterface::class.java).getTasks(skip = loadMorePressed * tasksPerPage)
                                tasksAll.addAll(tasks.tasks)
                            } catch (e:java.lang.Exception) {
                                Log.e("Tasks", "FAILED TO LOAD MORE$e")
                            } }
                    })
                    tasksRV.adapter = adapter
                    adapter.notifyDataSetChanged()

//                    adapter.notifyDataSetChanged()
                }
            }
            catch (e:java.lang.Exception) {
                Log.e("STATUS", "FAILED:$e")
//                Toast.makeText(this@MainActivity, "RERTROFIT FAILED", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem = menu?.findItem(R.id.taskSearchSV)
        val searchView = searchItem?.actionView as SearchView

        searchView.apply {
//            queryHint = getString(R.string.search_hint)
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