package com.geektech.intellect_memort.presentation.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseRecyclerViewHolder
import com.geektech.intellect_memort.databinding.ItemRandomNumberBinding
import com.geektech.intellect_memort.databinding.ItemRowBinding
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel

class RandomNumbersAdapter :
    ListAdapter<RandomNumbersModel, BaseRecyclerViewHolder<ViewBinding, RandomNumbersModel>>(
        differCallback
    ) {
    private var lastPosition: Int = 7

    inner class ViewHolder(binding: ItemRandomNumberBinding) :
        BaseRecyclerViewHolder<ItemRandomNumberBinding, RandomNumbersModel>(
            binding
        ) {
        init {
            if (absoluteAdapterPosition == lastPosition) {
                binding.container.setBackgroundColor(Color.TRANSPARENT)
                notifyItemChanged(lastPosition)
                binding.container.setBackgroundColor(Color.RED)
                lastPosition = absoluteAdapterPosition
                notifyItemChanged(absoluteAdapterPosition)
            }
        }

        override fun onBind(item: RandomNumbersModel?) {
            binding.itemNumber.text = item?.numbers.toString()
            setOnItemNextClickListener(lastPosition)
        }

        private fun setOnItemNextClickListener(row: Int) {
//            if ((absoluteAdapterPosition + 7) % 14 / row == 0) {
//                binding.container.setBackgroundColor(Color.RED)
//            } else {
//                binding.container.setBackgroundColor(Color.TRANSPARENT)
//            }
        }
    }

    fun setNextAndPreviousItemRow(row: Int) {
        lastPosition = row
    }

    inner class RowViewHolder(binding: ItemRowBinding) :
        BaseRecyclerViewHolder<ItemRowBinding, RandomNumbersModel>(
            binding
        ) {


        override fun onBind(item: RandomNumbersModel?) {
            binding.itemRow.text = item?.row.toString()
            setOnItemNextClickListener(lastPosition)
        }

        private fun setOnItemNextClickListener(row: Int) {
            if ((absoluteAdapterPosition + 7) % 14 / row == 0) {
                binding.itemRow.setTextColor(Color.RED)
            } else {
                binding.itemRow.setTextColor(Color.BLACK)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if ((position + 7) % 7 * 2 == 0) {
            R.layout.item_row
        } else {
            R.layout.item_random_number
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerViewHolder<ViewBinding, RandomNumbersModel> {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_random_number -> ViewHolder(
                ItemRandomNumberBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            R.layout.item_row -> RowViewHolder(ItemRowBinding.inflate(inflater, parent, false))
            else -> {
                throw IllegalAccessException("Invalid viewType provided")
            }
        }
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ViewBinding, RandomNumbersModel>,
        position: Int,
    ) {
        when (holder.itemViewType) {
            R.layout.item_random_number -> getItem(position)?.let {
                holder.onBind(it)
            }
            R.layout.item_row -> getItem(position)?.let {
                holder.onBind(it)
            }
        }

    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<RandomNumbersModel>() {
            override fun areItemsTheSame(
                oldItem: RandomNumbersModel,
                newItem: RandomNumbersModel,
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: RandomNumbersModel,
                newItem: RandomNumbersModel,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}