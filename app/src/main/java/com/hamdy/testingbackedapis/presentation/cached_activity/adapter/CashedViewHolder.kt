package com.hamdy.testingbackedapis.presentation.cached_activity.adapter

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.hamdy.testingbackedapis.databinding.CachedItemBinding
import com.hamdy.testingbackedapis.databinding.KeyValueItemBinding
import com.hamdy.testingbackedapis.domain.model.ParamsData
import com.hamdy.testingbackedapis.domain.model.RequestData

class CashedViewHolder(
    private val binding: CachedItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: RequestData) {
        binding.cachedMethode.text = item.requestMethod
        binding.cachedStatus.text = "Status Code: ${item.statusCode}"

    }
}