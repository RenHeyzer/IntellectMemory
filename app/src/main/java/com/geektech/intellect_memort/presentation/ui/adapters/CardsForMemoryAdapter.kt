package com.geektech.intellect_memort.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geektech.intellect_memort.databinding.ItemCardsBinding
import com.geektech.intellect_memort.presentation.models.CardsUI

class CardsForMemoryAdapter : ListAdapter<CardsUI, CardsForMemoryAdapter.ViewHolder>(DifferCards) {
    inner class ViewHolder(private val binding: ItemCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CardsUI) {
            Glide.with(binding.itemCardsImage)
                .load(item.url)
                .into(binding.itemCardsImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCardsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    object DifferCards : DiffUtil.ItemCallback<CardsUI>() {
        override fun areItemsTheSame(oldItem: CardsUI, newItem: CardsUI): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: CardsUI, newItem: CardsUI): Boolean {
            return oldItem == newItem
        }
    }

}