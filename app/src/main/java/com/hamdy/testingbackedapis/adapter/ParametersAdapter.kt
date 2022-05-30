package com.hamdy.testingbackedapis.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hamdy.testingbackedapis.databinding.KeyValueItemBinding

class ParametersAdapter : RecyclerView.Adapter<ParametersViewHolder>() {
    private var itemCount = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParametersViewHolder {
        val binding =
            KeyValueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ParametersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParametersViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return itemCount
    }

}