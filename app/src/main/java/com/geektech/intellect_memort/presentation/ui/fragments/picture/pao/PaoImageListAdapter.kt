package com.geektech.intellect_memort.presentation.ui.fragments.picture.pao

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.databinding.PictureRvItemBinding
import com.geektech.intellect_memort.domain.model.PictureImageModel
import com.geektech.intellect_memort.presentation.ui.fragments.picture.pao.PaoImageListAdapter.*


class PaoImageListAdapter : RecyclerView.Adapter<PaoImageListVH>() {


    private var onItemClickListener: ((PictureImageModel) -> Unit)? = null

    fun setOnClickListener(listener: (PictureImageModel) -> Unit) {
        onItemClickListener = listener
    }

    val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaoImageListVH {
        return PaoImageListVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.picture_rv_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PaoImageListVH, position: Int) {
        val imageModel = differ.currentList[position]
        holder.binding.apply {
            val bitmap = BitmapFactory.decodeFile(imageModel.imageUrl)
            image.setImageBitmap(bitmap)
            Log.e("adapter: ", bitmap.toString() + " " + imageModel.id)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class PaoImageListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = PictureRvItemBinding.bind(itemView)
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