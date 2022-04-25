package com.geektech.intellect_memort.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geektech.intellect_memort.common.extension.loadUrlWithCoil
import com.geektech.intellect_memort.databinding.ItemCardsBinding
import com.geektech.intellect_memort.presentation.models.CardsUI


class CardsAdapter : ListAdapter<CardsUI, CardsAdapter.ViewHolder>(DifferCards) {

    inner class ViewHolder(private val binding: ItemCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: CardsUI) {
            binding.itemNumberOfCards.text = absoluteAdapterPosition.plus(1).toString()
            binding.itemCardsImage.loadUrlWithCoil(item.url.toString())
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
