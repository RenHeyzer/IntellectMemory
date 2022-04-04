package com.geektech.intellect_memort.presentation.ui.fragments.picture.game.gameplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geektech.intellect_memort.common.base.SBaseDiffUtilItemCallback
import com.geektech.intellect_memort.databinding.PictureRvItemBinding
import com.geektech.intellect_memort.domain.models.PictureImageModel
import com.geektech.intellect_memort.presentation.ui.fragments.picture.game.gameplay.PlayImageListAdapter.PlayImageViewHolder

class PlayImageListAdapter : ListAdapter<PictureImageModel, PlayImageViewHolder>(
    SBaseDiffUtilItemCallback()
) {

    private var onItemClickListener: ((PictureImageModel) -> Unit)? = null

    fun setOnClickListener(listener: (PictureImageModel) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayImageViewHolder {
        return PlayImageViewHolder(
            PictureRvItemBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context),
                    parent,
                    false
                )
        )
    }


    override fun onBindViewHolder(holder: PlayImageViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }


    inner class PlayImageViewHolder(val binding: PictureRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: PictureImageModel) {
            Glide.with(binding.image)
                .load(item.imageUrl)
                .into(binding.image)
            binding.title.text = item.id
        }
    }
}