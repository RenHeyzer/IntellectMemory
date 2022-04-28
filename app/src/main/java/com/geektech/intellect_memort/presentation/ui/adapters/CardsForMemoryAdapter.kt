package com.geektech.intellect_memort.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseRecyclerViewHolder
import com.geektech.intellect_memort.common.extension.loadUrlWithCoil
import com.geektech.intellect_memort.databinding.ItemMultipleCardsBinding
import com.geektech.intellect_memort.databinding.ItemSingleCardsBinding
import com.geektech.intellect_memort.presentation.models.CardsUI

class CardsForMemoryAdapter(
    private val isSingleCardsOrMultiple: Int,
) : ListAdapter<CardsUI, BaseRecyclerViewHolder<ViewBinding, CardsUI>>(DifferCards) {


    inner class MultipleItemCardsViewHolder(binding: ItemMultipleCardsBinding) :
        BaseRecyclerViewHolder<ItemMultipleCardsBinding, CardsUI>(binding) {
        override fun onBind(item: CardsUI) {
            binding.itemCardsImage.loadUrlWithCoil(item.url.toString())
        }
    }

    inner class SingleItemCardsViewHolder(binding: ItemSingleCardsBinding) :
        BaseRecyclerViewHolder<ItemSingleCardsBinding, CardsUI>(binding) {
        override fun onBind(item: CardsUI) {
            binding.itemCardsImage.loadUrlWithCoil(item.url.toString())
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerViewHolder<ViewBinding, CardsUI> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_single_cards -> SingleItemCardsViewHolder(
                ItemSingleCardsBinding.inflate(
                    inflater,
                    parent,
                    false))

            R.layout.item_multiple_cards -> MultipleItemCardsViewHolder(
                ItemMultipleCardsBinding.inflate(
                    inflater,
                    parent,
                    false)
            )
            else -> {
                throw IllegalAccessException("Invalid viewType provided")
            }
        }
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ViewBinding, CardsUI>,
        position: Int,
    ) {
        when (holder.itemViewType) {
            R.layout.item_single_cards -> {
                val singleItemCardsViewHolder = holder as SingleItemCardsViewHolder
                singleItemCardsViewHolder.onBind(getItem(position))
            }
            R.layout.item_multiple_cards -> {
                val multipleItemCardsViewHolder = holder as MultipleItemCardsViewHolder
                getItem(position)?.let {
                    multipleItemCardsViewHolder.onBind(it)
                }
            }
        }
    }

    object DifferCards : DiffUtil.ItemCallback<CardsUI>() {
        override fun areItemsTheSame(oldItem: CardsUI, newItem: CardsUI): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: CardsUI, newItem: CardsUI): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSingleCardsOrMultiple == 1) {
            R.layout.item_single_cards
        } else {
            R.layout.item_multiple_cards
        }
    }
}