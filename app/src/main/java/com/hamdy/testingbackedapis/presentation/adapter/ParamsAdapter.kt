package com.hamdy.testingbackedapis.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamdy.testingbackedapis.databinding.KeyValueItemBinding
import com.hamdy.testingbackedapis.domain.model.ParamsData

class ParamsAdapter(private val onDeleteItemClickListener: ParametersViewHolder.OnKeyValueItemTouch) :
    RecyclerView.Adapter<ParametersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParametersViewHolder {
        val binding =
            KeyValueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ParametersViewHolder(binding, onDeleteItemClickListener)
    }

    override fun onBindViewHolder(holder: ParametersViewHolder, position: Int) {
        holder.bind(differ.currentList[position])

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val callBack = object : DiffUtil.ItemCallback<ParamsData>() {
        override fun areItemsTheSame(oldItem: ParamsData, newItem: ParamsData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ParamsData, newItem: ParamsData): Boolean {
            return oldItem == newItem
        }
    }
    var differ = AsyncListDiffer(this, callBack)
}