package com.geektech.intellect_memort.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.viewbinding.ViewBinding
import com.geektech.intellect_memort.common.base.BaseDiffUtilItemCallback
import com.geektech.intellect_memort.common.base.BaseRecyclerViewHolder
import com.geektech.intellect_memort.databinding.ItemStudentsBinding
import com.geektech.intellect_memort.domain.models.StudentsModel

class StudentsAdapter :
    PagingDataAdapter<StudentsModel, BaseRecyclerViewHolder<ViewBinding, StudentsModel>>(
        BaseDiffUtilItemCallback()
    ) {

    override fun onBindViewHolder(
        holder: BaseRecyclerViewHolder<ViewBinding, StudentsModel>,
        position: Int,
    ) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerViewHolder<ViewBinding, StudentsModel> {
        return StudentsViewHolder(
            ItemStudentsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    inner class StudentsViewHolder(
        binding: ItemStudentsBinding,
    ) : BaseRecyclerViewHolder<ItemStudentsBinding, StudentsModel>(binding) {

        override fun onBind(item: StudentsModel) = with(binding) {
            itemNumber.text = absoluteAdapterPosition.plus(1).toString()
            itemFullName.text = item.fullName
            itemSchool.text = item.branch
            itemPoint.text = item.points.toString()
        }
    }
}