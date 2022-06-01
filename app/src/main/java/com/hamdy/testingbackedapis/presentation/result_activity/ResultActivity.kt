package com.hamdy.testingbackedapis.presentation.result_activity

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import com.hamdy.testingbackedapis.common.Constants.REQUEST_DATA_KEY
import com.hamdy.testingbackedapis.data.local.RequestDbHelper
import com.hamdy.testingbackedapis.databinding.ActivityResultBinding
import com.hamdy.testingbackedapis.domain.model.RequestData
import com.hamdy.testingbackedapis.presentation.result_activity.ui.main.SectionsPagerAdapter

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val requestData = intent.extras?.get(REQUEST_DATA_KEY) as RequestData

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager,requestData)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val db = RequestDbHelper(this)
        db.addRequest(requestData)


    }
}