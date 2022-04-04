package com.geektech.intellect_memort.presentation.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseDiffUtilItemCallback
import com.geektech.intellect_memort.databinding.ItemRandomNumberBinding
import com.geektech.intellect_memort.databinding.ItemRowBinding
import com.geektech.intellect_memort.domain.models.NumbersModel

class NumbersAdapter :
    ListAdapter<NumbersModel, RecyclerView.ViewHolder>(
        BaseDiffUtilItemCallback()
    ) {
    private var lastPosition: Int = 6
    private var rowLastPosition: Int = 0

    inner class ViewHolder(private val binding: ItemRandomNumberBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun onBind(item: NumbersModel?) {
            binding.itemNumber.text = item?.number.toString()
            setOnItemNextClickListener()
        }

        private fun setOnItemNextClickListener() {
            when {
                absoluteAdapterPosition == lastPosition -> {
                    binding.strokeMaterial.setCardBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.strokeMaterial.strokeColor = Color.parseColor("#FF3EA7")
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                absoluteAdapterPosition + 1 == lastPosition -> {
                    binding.strokeMaterial.setCardBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.strokeMaterial.strokeColor = Color.parseColor("#FF3EA7")
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                absoluteAdapterPosition + 2 == lastPosition -> {
                    binding.strokeMaterial.setCardBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.strokeMaterial.strokeColor = Color.parseColor("#FF3EA7")
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                absoluteAdapterPosition + 3 == lastPosition -> {
                    binding.strokeMaterial.setCardBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.strokeMaterial.strokeColor = Color.parseColor("#FF3EA7")
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                absoluteAdapterPosition + 4 == lastPosition -> {
                    binding.strokeMaterial.setCardBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.strokeMaterial.strokeColor = Color.parseColor("#FF3EA7")
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                absoluteAdapterPosition + 5 == lastPosition -> {
                    binding.strokeMaterial.setCardBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.strokeMaterial.strokeColor = Color.parseColor("#FF3EA7")
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                else -> {
                    binding.strokeMaterial.setCardBackgroundColor(Color.parseColor("#DADADA"))
                    binding.strokeMaterial.strokeColor = Color.parseColor("#DADADA")
                    binding.itemNumber.setTextColor(Color.parseColor("#FF000000"))
                }
            }
            Log.e("anime", "viewHolder:$lastPosition ")
        }
    }

    fun setNextAndPreviousItemRow(row: Int, last: Int) {
        if (rowLastPosition >= 0 && lastPosition >= 6) {
            rowLastPosition = row
            lastPosition = last
        } else {
            rowLastPosition = 0
            lastPosition = 6
        }
    }

    inner class RowViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun onBind(position: Int) {
            val rowPosition = position / 7
            binding.itemRow.text = rowPosition.plus(1).toString()
            setOnItemNextClickListener()
        }

        private fun setOnItemNextClickListener() {
            if ((absoluteAdapterPosition + 6) == rowLastPosition - 1) {
                binding.itemRow.setTextColor(Color.CYAN)
            } else {
                binding.itemRow.setTextColor(Color.BLACK)

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 7 * 2 == 0) {
            R.layout.item_row
        } else {
            R.layout.item_random_number
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_row -> RowViewHolder(ItemRowBinding.inflate(inflater, parent, false))

            R.layout.item_random_number -> ViewHolder(
                ItemRandomNumberBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> {
                throw IllegalAccessException("Invalid viewType provided")
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (holder.itemViewType) {
            R.layout.item_row -> {
                val rowViewHolder = holder as RowViewHolder
                if (position % 7 * 2 == 0) {
                    rowViewHolder.onBind(position)
                }
            }
            R.layout.item_random_number -> {
                val randomViewHolder = holder as ViewHolder
                getItem(position)?.let {
                    randomViewHolder.onBind(it)
                }
            }
        }
    }
}