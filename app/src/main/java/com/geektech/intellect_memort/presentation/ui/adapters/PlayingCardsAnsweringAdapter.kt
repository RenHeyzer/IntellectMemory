package com.geektech.intellect_memort.presentation.ui.adapters

import android.content.ClipData
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geektech.intellect_memort.common.extension.loadUrlWithCoil
import com.geektech.intellect_memort.common.utils.PlayingCardsAnsweringListener
import com.geektech.intellect_memort.databinding.ItemCardsBinding
import com.geektech.intellect_memort.presentation.models.CardsUI
import com.geektech.intellect_memort.presentation.ui.adapters.dradlistener.DragListener


class PlayingCardsAnsweringAdapter(
    private var list: List<CardsUI>,
    private val listener: PlayingCardsAnsweringListener?,
    private val isCardDragging: Boolean,
) : RecyclerView.Adapter<PlayingCardsAnsweringAdapter.PlayingCardsViewHolder>(),
    View.OnTouchListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayingCardsViewHolder {
        return PlayingCardsViewHolder(
            ItemCardsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    fun updateList(list: MutableList<CardsUI>) {
        this.list = list
    }

    fun getList(): MutableList<CardsUI> = this.list.toMutableList()

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = View.DragShadowBuilder(v)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v?.startDragAndDrop(data, shadowBuilder, v, 0)
                } else {
                    v?.startDrag(data, shadowBuilder, v, 0)
                }
                return true
            }
        }
        return false
    }

    val dragInstance: DragListener?
        get() = if (listener != null) {
            DragListener(listener)
        } else {
            Log.e(javaClass::class.simpleName, "Listener not initialized")
            null
        }

    override fun onBindViewHolder(holder: PlayingCardsViewHolder, position: Int) {
        if (isCardDragging)
            if (!list[position].emptySpace!!)
                holder.binding.cardView?.setOnTouchListener(this)
        holder.binding.cardView.setOnDragListener(DragListener(listener!!))
        holder.onBind(list[position])
    }

    inner class PlayingCardsViewHolder(val binding: ItemCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: CardsUI) {
            binding.run {
                itemNumberOfCards.text = absoluteAdapterPosition.plus(1).toString()
                itemCardsImage.loadUrlWithCoil(model.url.toString())
            }
        }
    }
}