package com.example.habrfreelance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.RetrofitObj
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val tasksRV : RecyclerView = findViewById(R.id.tasksRV)
        Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show()
        GlobalScope.launch() {
            try {


            val tasks = RetrofitObj.retrofit.create(APIinterface::class.java).getTasks()
//            Log.e("Tasks", tasks.toString())
            runOnUiThread {
                val adapter = TaskAdapter(tasks.tasks, itemOnClick = {}, tagOnClick = {})
                tasksRV.adapter = adapter
            }
            }
            catch (e:java.lang.Exception) {
                Log.e("STATUS", "FAILED")
//                Toast.makeText(this@MainActivity, "RERTROFIT FAILED", Toast.LENGTH_SHORT).show()
            }
        }
    }
}