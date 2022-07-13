package com.vama.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    val viewModel = MainActivityViewModel()
    var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyler_view)
        val imagesAdapter = ImagesListAdapter(mutableListOf())
        recyclerView!!.adapter = imagesAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(this);
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                viewModel.nextPage()
            }
        })

        viewModel.getImages()

        viewModel.imagesLiveData.observe(this@MainActivity) { list ->
            Log.d("tag", list.toString())
            imagesAdapter.setData(list.imageURLs)
//            recyclerView!!.scrollToPosition(0)
            imagesAdapter.notifyDataSetChanged()
        }
    }
}