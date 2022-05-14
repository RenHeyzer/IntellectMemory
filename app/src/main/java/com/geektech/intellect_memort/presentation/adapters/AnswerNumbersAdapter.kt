package com.geektech.intellect_memort.presentation.adapters

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.base.BaseDiffUtilItemCallback
import com.geektech.intellect_memort.common.base.BaseRecyclerViewHolder
import com.geektech.intellect_memort.databinding.ItemAnswerNumbersBinding
import com.geektech.intellect_memort.databinding.ItemRowBinding
import com.geektech.intellect_memort.domain.models.AnswerNumbersModel

class AnswerNumbersAdapter(
    val onInput: (position: Int, number: Int?) -> Unit,
) : ListAdapter<AnswerNumbersModel, BaseRecyclerViewHolder<ViewBinding, AnswerNumbersModel>>(
    BaseDiffUtilItemCallback()
) {

    private var lastPosition: Int = 6
    private var rowLastPosition: Int = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerViewHolder<ViewBinding, AnswerNumbersModel> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_answer_numbers -> {
                ViewHolder(
                    ItemAnswerNumbersBinding.inflate(
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
            R.layout.item_answer_numbers -> {
                val viewHolder = holder as ViewHolder
                viewHolder.onBind(getItem(position))
            }
            R.layout.item_row -> {
                val rowViewHolder = holder as RowViewHolder
                rowViewHolder.onBind(getItem(position))
            }
        }

    }

    inner class ViewHolder(
        binding: ItemAnswerNumbersBinding,
    ) : BaseRecyclerViewHolder<ItemAnswerNumbersBinding, AnswerNumbersModel>(
        binding
    ) {

        init {
            binding.itemNumber.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    onInput(
                        absoluteAdapterPosition,
                        binding.itemNumber.text.toString().toIntOrNull()
                    )
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }

        override fun onBind(item: AnswerNumbersModel?) {
            this.setupItemHighlighting()
            binding.apply {
                itemNumber.setText(item?.answerNumber?.toString() ?: "")
            }
        }

        private fun setupItemHighlighting() = with(binding) {
            Log.e("promo", "setupItemHighlighting: $absoluteAdapterPosition")
            when {
                (absoluteAdapterPosition == lastPosition) -> {
                    itemNumber.setTextColor(Color.BLUE)
                    itemNumber.isVisible = true
                    strokeMaterial.strokeColor = Color.BLUE
                }
                (absoluteAdapterPosition + 1 == lastPosition) -> {
                    itemNumber.setTextColor(Color.BLUE)
                    itemNumber.isVisible = true
                    strokeMaterial.strokeColor = Color.BLUE
                }
                (absoluteAdapterPosition + 2 == lastPosition) -> {
                    itemNumber.setTextColor(Color.BLUE)
                    itemNumber.isVisible = true
                    strokeMaterial.strokeColor = Color.BLUE
                }
                (absoluteAdapterPosition + 3 == lastPosition) -> {
                    itemNumber.setTextColor(Color.BLUE)
                    itemNumber.isVisible = true
                    strokeMaterial.strokeColor = Color.BLUE
                }
                (absoluteAdapterPosition + 4 == lastPosition) -> {
                    itemNumber.setTextColor(Color.BLUE)
                    itemNumber.isVisible = true
                    strokeMaterial.strokeColor = Color.BLUE
                }
                (absoluteAdapterPosition + 5 == lastPosition) -> {
                    itemNumber.setTextColor(Color.BLUE)
                    itemNumber.isVisible = true
                    strokeMaterial.strokeColor = Color.BLUE
                }
                else -> {
                    itemNumber.setTextColor(Color.BLACK)
                    itemNumber.isVisible = false
                    strokeMaterial.strokeColor = Color.TRANSPARENT
                }
            }
        }
    }

    fun setNextAndPrevious(lastPosition: Int, rowLastPosition: Int) {
        if (this.lastPosition >= 6 && this.rowLastPosition >= 0) {
            this.lastPosition = lastPosition
            this.rowLastPosition = rowLastPosition
        } else {
            this.lastPosition = 6
            this.rowLastPosition = 0
        }
    }

    inner class RowViewHolder(binding: ItemRowBinding) :
        BaseRecyclerViewHolder<ItemRowBinding, AnswerNumbersModel>(
            binding
        ) {

        override fun onBind(item: AnswerNumbersModel?) {
            val rowPosition = absoluteAdapterPosition / 7
            binding.itemRow.text = rowPosition.plus(1).toString()
            this.setupItemHighlighting()
        }

        private fun setupItemHighlighting() {
            if (absoluteAdapterPosition == rowLastPosition) {
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
            R.layout.item_answer_numbers
        }
    }
}