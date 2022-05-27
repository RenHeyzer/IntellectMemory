package com.geektech.intellect_memort.presentation.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.geektech.intellect_memort.common.base.BaseDiffUtilItemCallback
import com.geektech.intellect_memort.common.base.BaseRecyclerViewHolder
import com.geektech.intellect_memort.common.extension.load
import com.geektech.intellect_memort.databinding.ItemCardsBinding
import com.geektech.intellect_memort.presentation.models.CardsUI

class CardsResultAdapter(
    private val checkList: ArrayList<Boolean>,
) : ListAdapter<CardsUI, BaseRecyclerViewHolder<ViewBinding, CardsUI>>(
    BaseDiffUtilItemCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerViewHolder<ViewBinding, CardsUI> {
        return CardViewHolder(ItemCardsBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    inner class CardViewHolder(binding: ItemCardsBinding) :
        BaseRecyclerViewHolder<ItemCardsBinding, CardsUI>(binding) {

        override fun onBind(item: CardsUI?) = with(binding) {
            item?.url?.let { itemCardsImage.load(it) }

            if (checkList[absoluteAdapterPosition]) {
                binding.stroke.strokeColor = Color.GREEN
            } else {
                binding.stroke.strokeColor = Color.RED
            }
        }
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ViewBinding, CardsUI>,
        position: Int,
    ) {
        holder.onBind(getItem(position))
    }
}