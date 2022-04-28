package com.geektech.intellect_memort.presentation.ui.adapters.dradlistener

import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.utils.PlayingCardsAnsweringListener
import com.geektech.intellect_memort.presentation.models.CardsUI
import com.geektech.intellect_memort.presentation.ui.adapters.PlayingCardsAnsweringAdapter

class DragListener internal constructor(private val listener: PlayingCardsAnsweringListener) :
    View.OnDragListener {
    private var isDropped = false
    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                isDropped = true
                var positionTarget = -1
                val viewSource = event.localState as View?
                val viewId = v.id
                val frameLayoutItem = R.id.cardView
                val emptyTextView1 = R.id.empty_list_text_view_1
                val emptyTextView2 = R.id.empty_list_text_view_2
                val recyclerView1 = R.id.rv_cards
                val recyclerView2 = R.id.rv_cards_answering
                when (viewId) {
                    frameLayoutItem, emptyTextView1, emptyTextView2, recyclerView1, recyclerView2 -> {
                        val target: RecyclerView
                        when (viewId) {
                            emptyTextView1, recyclerView1 -> target =
                                v.rootView.findViewById<View>(recyclerView1) as RecyclerView
                            emptyTextView2, recyclerView2 -> target =
                                v.rootView.findViewById<View>(recyclerView2) as RecyclerView
                            else -> {
                                target = v.parent as RecyclerView
                                positionTarget = v.tag as Int
                            }
                        }
                        if (viewSource != null) {
                            val source = viewSource.parent as RecyclerView
                            val adapterSource = source.adapter as PlayingCardsAnsweringAdapter?
                            val positionSource = viewSource.tag as Int
                            val list: CardsUI? = adapterSource?.getList()?.get(positionSource)
                            val listSource = adapterSource?.getList()?.apply {
                                removeAt(positionSource)
                            }
                            listSource?.let { adapterSource.updateList(it) }
                            adapterSource?.notifyDataSetChanged()
                            val adapterTarget = target.adapter as PlayingCardsAnsweringAdapter?
                            val customListTarget = adapterTarget?.getList()
                            if (positionTarget >= 0) {
                                list?.let { customListTarget?.set(positionTarget, it) }
                            } else {
                                list?.let { customListTarget?.add(it) }
                            }
                            customListTarget?.let { adapterTarget.updateList(it) }
                            adapterTarget?.notifyDataSetChanged()
                            if (source.id == recyclerView2 && adapterSource?.itemCount ?: 0 < 1) {
                                listener.setEmptyList(View.VISIBLE, recyclerView2, emptyTextView2)
                            }
                            if (viewId == emptyTextView2) {
                                listener.setEmptyList(View.GONE, recyclerView2, emptyTextView2)
                            }
                            if (source.id == recyclerView1 && adapterSource?.itemCount ?: 0 < 1) {
                                listener.setEmptyList(View.VISIBLE, recyclerView1, emptyTextView1)
                            }
                            if (viewId == emptyTextView1) {
                                listener.setEmptyList(View.GONE, recyclerView1, emptyTextView1)
                            }
                        }
                    }
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        return true
    }
}