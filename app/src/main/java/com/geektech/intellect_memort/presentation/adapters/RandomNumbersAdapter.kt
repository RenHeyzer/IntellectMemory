package com.geektech.intellect_memort.presentation.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.databinding.ItemRandomNumberBinding
import com.geektech.intellect_memort.databinding.ItemRowBinding
import com.geektech.intellect_memort.domain.models.RandomNumbersModel

class RandomNumbersAdapter :
    ListAdapter<RandomNumbersModel, RecyclerView.ViewHolder>(
        differCallback
    ) {
    private var lastPosition: Int = 14
    private var rowLastPosition: Int = 7

    inner class ViewHolder(private val binding: ItemRandomNumberBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun onBind(item: RandomNumbersModel?) {
            binding.itemNumber.text = item?.number.toString()
            setOnItemNextClickListener()
        }

        private fun setOnItemNextClickListener() {
            when {
                (absoluteAdapterPosition + 6) == lastPosition - 2 -> {
                    binding.container.setBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                (absoluteAdapterPosition + 6) == lastPosition - 3 -> {
                    binding.container.setBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                (absoluteAdapterPosition + 6) == lastPosition - 4 -> {
                    binding.container.setBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                (absoluteAdapterPosition + 6) == lastPosition - 5 -> {
                    binding.container.setBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                (absoluteAdapterPosition + 6) == lastPosition - 6 -> {
                    binding.container.setBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                (absoluteAdapterPosition + 6) == lastPosition - 7 -> {
                    binding.container.setBackgroundColor(Color.parseColor("#FF3EA7"))
                    binding.itemNumber.setTextColor(Color.parseColor("#FFFFFFFF"))
                }
                else -> {
                    binding.container.setBackgroundColor(Color.TRANSPARENT)
                    binding.itemNumber.setTextColor(Color.parseColor("#FF000000"))
                }
            }
            Log.e("anime", "viewHolder:$lastPosition ")
        }
    }

    fun setNextAndPreviousItemRow(row: Int, last: Int) {
        if (rowLastPosition >= 7 && lastPosition >= 14) {
            rowLastPosition = row
            lastPosition = last
            notifyItemChanged(lastPosition, true)
        } else {
            rowLastPosition = 7
            lastPosition = 14
        }
    }

    inner class RowViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(
            binding.root
        ) {
        fun onBind() {
            val rowPosition = absoluteAdapterPosition / 7
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
        return if ((position - 7) % 7 * 2 == 0) {
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
                rowViewHolder.onBind()
            }
            R.layout.item_random_number -> {
                val randomViewHolder = holder as ViewHolder
                getItem(position)?.let {
                    randomViewHolder.onBind(it)
                }
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