package com.geektech.intellect_memory.presentation.ui.fragments.picture.pao.pictures_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geektech.intellect_memory.common.base.SBaseDiffUtilItemCallback
import com.geektech.intellect_memory.databinding.PictureRvItemBinding
import com.geektech.intellect_memory.domain.models.PictureImageModel
import com.geektech.intellect_memory.presentation.ui.fragments.picture.pao.pictures_list.PaoImageListAdapter.PaoImageViewHolder


class PaoImageListAdapter : ListAdapter<PictureImageModel, PaoImageViewHolder>(
    SBaseDiffUtilItemCallback()
) {

    private var onItemClickListener: ((PictureImageModel) -> Unit)? = null

    fun setOnClickListener(listener: (PictureImageModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaoImageViewHolder {
        return PaoImageViewHolder(PictureRvItemBinding
            .inflate(LayoutInflater
                .from(parent.context),
                parent,
                false))
    }


    override fun onBindViewHolder(holder: PaoImageViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }


    inner class PaoImageViewHolder(val binding: PictureRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: PictureImageModel) {
            Glide.with(binding.image)
                .load(item.imageUrl)
                .into(binding.image)
            binding.title.text = item.id

        }
    }
}