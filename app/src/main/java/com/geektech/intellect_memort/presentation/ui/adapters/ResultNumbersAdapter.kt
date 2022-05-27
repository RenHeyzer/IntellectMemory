package com.geektech.intellect_memort.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseDiffUtilItemCallback
import com.geektech.intellect_memort.common.base.BaseRecyclerViewHolder
import com.geektech.intellect_memort.databinding.ItemResultNumbersFalseBinding
import com.geektech.intellect_memort.databinding.ItemResultNumbersTrueBinding
import com.geektech.intellect_memort.databinding.ItemResultNumbersUnknownBinding
import com.geektech.intellect_memort.databinding.ItemRowBinding
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel

class ResultNumbersAdapter(
    private val checkList: ArrayList<Boolean>
) :
    ListAdapter<AnswerNumbersModel, BaseRecyclerViewHolder<ViewBinding, AnswerNumbersModel>>(
        BaseDiffUtilItemCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerViewHolder<ViewBinding, AnswerNumbersModel> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_result_numbers_true -> {
                ViewHolder(
                    ItemResultNumbersTrueBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_result_numbers_false -> {
                ViewHolderFalse(
                    ItemResultNumbersFalseBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_result_numbers_unknown -> {
                UnknownViewHolder(
                    ItemResultNumbersUnknownBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            R.layout.item_row -> {
                RowViewHolder(
                    ItemRowBinding.inflate(
                        inflater,
                        parent,
                        false
                    )
                )
            }
            else -> {
                throw IllegalAccessException("Invalid viewType provided")
            }
        }
    }

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ViewBinding, AnswerNumbersModel>,
        position: Int,
    ) {
        when (holder.itemViewType) {
            R.layout.item_result_numbers_true -> {
                val viewHolder = holder as ViewHolder
                viewHolder.onBind(getItem(position))
            }
            R.layout.item_result_numbers_false -> {
                val viewHolderFalse = holder as ViewHolderFalse
                viewHolderFalse.onBind(getItem(position))
            }
            R.layout.item_result_numbers_unknown -> {
                val unknownViewHolder = holder as UnknownViewHolder
                unknownViewHolder.onBind(getItem(position))
            }
            R.layout.item_row -> {
                val rowViewHolder = holder as RowViewHolder
                rowViewHolder.onBind(getItem(position))
            }
        }

    }

    inner class ViewHolder(
        binding: ItemResultNumbersTrueBinding,
    ) : BaseRecyclerViewHolder<ItemResultNumbersTrueBinding, AnswerNumbersModel>(
        binding
    ) {

        override fun onBind(item: AnswerNumbersModel?) {
            binding.apply {
                itemNumber.text = item?.answerNumber?.toString()
            }
        }
    }

    inner class ViewHolderFalse(
        binding: ItemResultNumbersFalseBinding,
    ) : BaseRecyclerViewHolder<ItemResultNumbersFalseBinding, AnswerNumbersModel>(
        binding
    ) {

        override fun onBind(item: AnswerNumbersModel?) {
            binding.apply {
                itemNumber.text = item?.answerNumber?.toString()
            }
        }
    }

    inner class RowViewHolder(binding: ItemRowBinding) :
        BaseRecyclerViewHolder<ItemRowBinding, AnswerNumbersModel>(
            binding
        ) {

        override fun onBind(item: AnswerNumbersModel?) {
            val rowPosition = absoluteAdapterPosition / 7
            binding.itemRow.text = rowPosition.plus(1).toString()
        }
    }

    inner class UnknownViewHolder(binding: ItemResultNumbersUnknownBinding) :
        BaseRecyclerViewHolder<ItemResultNumbersUnknownBinding, AnswerNumbersModel>(
            binding
        ) {

        override fun onBind(item: AnswerNumbersModel?) {
            binding.itemNumber.text = item?.answerNumber.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            (position - 7) % 7 * 2 == 0 -> {
                R.layout.item_row
            }
            !checkList[position] && getItem(position)?.answerNumber != null -> {
                R.layout.item_result_numbers_false
            }
            checkList[position] -> {
                R.layout.item_result_numbers_true
            }
            getItem(position)?.answerNumber == null -> {
                R.layout.item_result_numbers_unknown
            }
            else -> {
                R.layout.item_result_numbers_unknown
            }
        }
    }
}