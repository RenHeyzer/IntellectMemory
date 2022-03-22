package com.geektech.intellect_memort.common.base

import androidx.recyclerview.widget.DiffUtil

class BaseDiffUtilItemCallback<T : IBaseDiffModel> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.password == newItem.password
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}