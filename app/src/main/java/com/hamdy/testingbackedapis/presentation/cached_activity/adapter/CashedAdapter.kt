package com.hamdy.testingbackedapis.presentation.cached_activity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamdy.testingbackedapis.databinding.CachedItemBinding
import com.hamdy.testingbackedapis.databinding.KeyValueItemBinding
import com.hamdy.testingbackedapis.domain.model.ParamsData
import com.hamdy.testingbackedapis.domain.model.RequestData
import com.hamdy.testingbackedapis.presentation.main_activity.adapter.ParametersViewHolder

class CashedAdapter :
    RecyclerView.Adapter<CashedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashedViewHolder {
        val binding =
            CachedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CashedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CashedViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val callBack = object : DiffUtil.ItemCallback<RequestData>() {
        override fun areItemsTheSame(oldItem: RequestData, newItem: RequestData): Boolean {
            return oldItem.headers == newItem.headers
        }

        override fun areContentsTheSame(oldItem: RequestData, newItem: RequestData): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, callBack)
}