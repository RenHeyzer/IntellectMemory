package com.geektech.intellect_memort.presentation.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseRecyclerViewHolder
import com.geektech.intellect_memort.common.extension.disable
import com.geektech.intellect_memort.common.extension.enable
import com.geektech.intellect_memort.databinding.ItemAnswerNumbersBinding
import com.geektech.intellect_memort.databinding.ItemRowBinding
import com.geektech.intellect_memort.presentation.models.RandomNumbersModel

class AnswerRandomNumbersAdapter(
    val list: ArrayList<RandomNumbersModel>,
) : RecyclerView.Adapter<BaseRecyclerViewHolder<ViewBinding, RandomNumbersModel>>(
) {
    private var lastPosition: Int = 14
    private var rowLastPosition: Int = 7


    override fun getItemCount(): Int = list.size

    inner class ViewHolder(binding: ItemAnswerNumbersBinding) :
        BaseRecyclerViewHolder<ItemAnswerNumbersBinding, RandomNumbersModel>(
            binding
        ) {
        init {
            binding.itemNumber.addTextChangedListener {
                list.add(RandomNumbersModel(it.toString().toIntOrNull() ?: 0, 0))
            }
        }

        override fun onBind(item: RandomNumbersModel?) {
            setOnItemNextClickListener()
        }

        @SuppressLint("ResourceAsColor")
        private fun setOnItemNextClickListener() {
            when {
                (absoluteAdapterPosition + 6) == lastPosition - 2 -> {
                    binding.container.strokeColor = Color.parseColor("#4446AD")
                    binding.itemNumber.setTextColor(Color.parseColor("#4446AD"))
                    binding.itemNumber.enable()
                }
                (absoluteAdapterPosition + 6) == lastPosition - 3 -> {
                    binding.container.strokeColor = Color.parseColor("#4446AD")
                    binding.itemNumber.setTextColor(Color.parseColor("#4446AD"))
                    binding.itemNumber.enable()
                }
                (absoluteAdapterPosition + 6) == lastPosition - 4 -> {
                    binding.container.strokeColor = Color.parseColor("#4446AD")
                    binding.itemNumber.setTextColor(Color.parseColor("#4446AD"))
                    binding.itemNumber.enable()
                }
                (absoluteAdapterPosition + 6) == lastPosition - 5 -> {
                    binding.container.strokeColor = Color.parseColor("#4446AD")
                    binding.itemNumber.setTextColor(Color.parseColor("#4446AD"))
                    binding.itemNumber.enable()
                }
                (absoluteAdapterPosition + 6) == lastPosition - 6 -> {
                    binding.container.strokeColor = Color.parseColor("#4446AD")
                    binding.itemNumber.setTextColor(Color.parseColor("#4446AD"))
                    binding.itemNumber.enable()
                }
                (absoluteAdapterPosition + 6) == lastPosition - 7 -> {
                    binding.container.strokeColor = Color.parseColor("#4446AD")
                    binding.itemNumber.setTextColor(Color.parseColor("#4446AD"))
                    binding.itemNumber.enable()
                }
                else -> {
                    binding.container.strokeColor = Color.TRANSPARENT
                    binding.itemNumber.setTextColor(R.color.black)
                    binding.itemNumber.disable()
                }
            }
            Log.e("anime", "viewHolder:$lastPosition ")

        }
    }

    fun setNextAndPreviousItemRow(row: Int, last: Int) {
        if (rowLastPosition >= 7 && lastPosition >= 14) {
            rowLastPosition = row
            lastPosition = last
        } else {
            rowLastPosition = 7
            lastPosition = 14
        }
    }

    inner class RowViewHolder(binding: ItemRowBinding) :
        BaseRecyclerViewHolder<ItemRowBinding, RandomNumbersModel>(
            binding
        ) {
        override fun onBind(item: RandomNumbersModel?) {
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
    ): BaseRecyclerViewHolder<ViewBinding, RandomNumbersModel> {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {

            R.layout.item_row -> RowViewHolder(
                ItemRowBinding.inflate(
                    inflater,
                    parent,
                    false))

            R.layout.item_random_number -> ViewHolder(
                ItemAnswerNumbersBinding.inflate(
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

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ViewBinding, RandomNumbersModel>,
        position: Int,
    ) {
        when (holder.itemViewType) {
            R.layout.item_row -> holder.onBind(list[position])
            R.layout.item_random_number -> {
                holder.onBind(list[position])
                holder.setIsRecyclable(false)
            }
        }
    }

//    companion object {
//        val differCallback = object : DiffUtil.ItemCallback<RandomNumbersModel>() {
//            override fun areItemsTheSame(
//                oldItem: RandomNumbersModel,
//                newItem: RandomNumbersModel,
//            ): Boolean {
//                return oldItem == newItem
//            }
//
//            override fun areContentsTheSame(
//                oldItem: RandomNumbersModel,
//                newItem: RandomNumbersModel,
//            ): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
}