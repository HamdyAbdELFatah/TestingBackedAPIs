package com.hamdy.testingbackedapis

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hamdy.testingbackedapis.adapter.ParametersAdapter
import com.hamdy.testingbackedapis.databinding.ActivityMainBinding

private lateinit var requestMethods: Array<String>

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val methodSpinner = binding.methodSpinner
        methodSpinner.onItemSelectedListener = this
        requestMethods = resources.getStringArray(R.array.request_method_spinner)

        val headersRecyclerView = binding.headersRecyclerView
        val parametersRecyclerView = binding.parametersRecyclerView
        headersRecyclerView.layoutManager = LinearLayoutManager(this)
        parametersRecyclerView.layoutManager = LinearLayoutManager(this)
        headersRecyclerView.adapter = ParametersAdapter()
        parametersRecyclerView.adapter = ParametersAdapter()

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, index: Int, p3: Long) {
        ///Toast.makeText(this, requestMethods[index], Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }
}