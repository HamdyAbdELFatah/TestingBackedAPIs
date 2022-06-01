package com.hamdy.testingbackedapis.presentation.cached_activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamdy.testingbackedapis.R
import com.hamdy.testingbackedapis.common.Constants.GET_METHODE
import com.hamdy.testingbackedapis.common.Constants.POST_METHODE
import com.hamdy.testingbackedapis.data.local.RequestDbHelper
import com.hamdy.testingbackedapis.databinding.ActivityCachedBinding
import com.hamdy.testingbackedapis.presentation.cached_activity.adapter.CashedAdapter


class CachedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCachedBinding
    private lateinit var cashedAdapter:CashedAdapter
    private lateinit var db: RequestDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCachedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = RequestDbHelper(this)
        val arr = db.getAllRequests()
        val cashedRecyclerView = binding.cachedRecycler
        cashedRecyclerView.layoutManager = LinearLayoutManager(this)
        cashedAdapter = CashedAdapter()
        cashedRecyclerView.adapter = cashedAdapter
        cashedAdapter.differ.submitList(arr)
        for(i in arr)
            Log.d("cached", "onCreate: ${i.statusCode} ${i.requestMethod}")
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cashed_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_by_time -> {
                val arr = db.getSortedRequestsByTime()
                cashedAdapter.differ.submitList(arr)
                true
            }
            R.id.get_filter -> {
                val arr = db.getRequestsByMethod(GET_METHODE)
                cashedAdapter.differ.submitList(arr)
                true
            }
            R.id.post_filter -> {
                val arr = db.getRequestsByMethod(POST_METHODE)
                cashedAdapter.differ.submitList(arr)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}