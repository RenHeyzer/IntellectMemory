package com.geektech.intellect_memory.presentation.ui.adapters

import android.content.ClipData
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.geektech.intellect_memory.common.extension.load
import com.geektech.intellect_memory.common.utils.PlayingCardsAnsweringListener
import com.geektech.intellect_memory.databinding.ItemCardsBinding
import com.geektech.intellect_memory.presentation.models.CardsUI
import com.geektech.intellect_memory.presentation.ui.adapters.dradlistener.DragListener


class PlayingCardsAnsweringAdapter(
    private var list: List<CardsUI>,
    private val listener: PlayingCardsAnsweringListener?,
    private val showMistake: () -> Unit,
) : RecyclerView.Adapter<PlayingCardsAnsweringAdapter.PlayingCardsViewHolder>() {

    var scrollDistance: Int = 0
    private lateinit var scrollListener: NestedScrollView

    fun getScrollDistance(scrollDistance: Int) {
        this.scrollDistance = scrollDistance
    }

    fun setOnScrollView(scrollListener: NestedScrollView) {
        this.scrollListener = scrollListener
    }

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

    val dragInstance: DragListener?
        get() = if (listener != null) {
            DragListener(scrollListener, listener) { showMistake() }
        } else {
            Log.e(javaClass::class.simpleName, "Listener not initialized")
            null
        }

    override fun onBindViewHolder(holder: PlayingCardsViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    inner class PlayingCardsViewHolder(val binding: ItemCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(model: CardsUI) {
            binding.run {
                itemNumberOfCards.text = absoluteAdapterPosition.plus(1).toString()
                itemCardsImage.load(model.url.toString())
                cardView.tag = absoluteAdapterPosition
                if (model.emptySpace == false) {
                    cardView.setOnLongClickListener { view ->
                        val data = ClipData.newPlainText("", "")
                        val shadowBuilder = View.DragShadowBuilder(view)
                        view.startDrag(data, shadowBuilder, view, 0)
                        view.visibility = View.INVISIBLE
                        true
                    }
                }
                cardView.setOnDragListener(listener?.let {
                    DragListener(scrollListener,
                        listener) { showMistake() }
                })
            }
        }
    }
}