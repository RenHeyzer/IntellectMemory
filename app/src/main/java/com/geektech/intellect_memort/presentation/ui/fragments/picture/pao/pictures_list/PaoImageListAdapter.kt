package com.geektech.intellect_memort.presentation.ui.fragments.picture.pao.pictures_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geektech.intellect_memort.databinding.PictureRvItemBinding
import com.geektech.intellect_memort.domain.models.PictureImageModel
import com.geektech.intellect_memort.presentation.ui.fragments.picture.pao.pictures_list.PaoImageListAdapter.PaoImageViewHolder


class PaoImageListAdapter : ListAdapter<PictureImageModel, PaoImageViewHolder>(diffCallback) {

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


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<PictureImageModel>() {
            override fun areItemsTheSame(
                oldItem: PictureImageModel,
                newItem: PictureImageModel,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: PictureImageModel,
                newItem: PictureImageModel,
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}