package com.hamdy.testingbackedapis.presentation.adapter

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.hamdy.testingbackedapis.databinding.KeyValueItemBinding
import com.hamdy.testingbackedapis.domain.model.ParamsData

class ParametersViewHolder(
    private val binding: KeyValueItemBinding,
    private val onDeleteItemClickListener: OnKeyValueItemTouch
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ParamsData) {
        binding.keyField.setText(item.key)
        binding.valueField.setText(item.value)
        binding.removeField.setOnClickListener {
            onDeleteItemClickListener.onDeleteClicked(adapterPosition, item.type)
        }
        binding.keyField.addTextChangedListener { key ->
            onDeleteItemClickListener.onKeyChange(adapterPosition, key.toString(), item.type)
        }
        binding.valueField.addTextChangedListener { value ->
            onDeleteItemClickListener.onValueChange(adapterPosition, value.toString(), item.type)

        }
    }

    public interface OnKeyValueItemTouch {
        fun onDeleteClicked(position: Int, type: String)
        fun onKeyChange(position: Int, key: String, type: String)
        fun onValueChange(position: Int, value: String, type: String)
    }
}