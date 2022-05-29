package com.geektech.intellect_memort.presentation.ui.adapters.dradlistener

import android.util.Log
import android.view.DragEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.geektech.intellect_memort.R
import com.geektech.intellect_memort.common.utils.PlayingCardsAnsweringListener
import com.geektech.intellect_memort.presentation.models.CardsUI
import com.geektech.intellect_memort.presentation.ui.adapters.PlayingCardsAnsweringAdapter
import kotlin.math.roundToInt

class DragListener internal constructor(
    private val scrollListener: NestedScrollView,
    private val listener: PlayingCardsAnsweringListener,
    private val showMistake: () -> Unit,
) :
    View.OnDragListener {

    var isDropped = false
    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {}
            DragEvent.ACTION_DRAG_ENTERED -> {}
            DragEvent.ACTION_DRAG_EXITED -> {}
            DragEvent.ACTION_DRAG_LOCATION -> {
                val viewSource = event.localState as View
                val source = viewSource.parent as RecyclerView
                val adapterSource = source.adapter as PlayingCardsAnsweringAdapter

                val y = event.y.roundToInt()
                val translatedY: Int = y - adapterSource.scrollDistance
                Log.e("smooth", translatedY.toString())
                if (translatedY < 130) {
                    scrollListener.smoothScrollBy(0, 30)
                }
            }
            DragEvent.ACTION_DROP -> {
                isDropped = true
                var positionSource = -1
                var positionTarget = -1
                val viewSource = event.localState as View
                if (v.id == R.id.cardView || v.id == R.id.empty_list_text_view_1) {
                    val target: RecyclerView
                    if (v.id == R.id.empty_list_text_view_1) {
                        target =
                            v.rootView.findViewById<View>(R.id.rv_cards_answering) as RecyclerView
                    } else {
                        target = v.parent as RecyclerView
                        positionTarget = v.tag as Int
                    }
                    val source = viewSource.parent as RecyclerView

                    val adapterSource = source.adapter as PlayingCardsAnsweringAdapter
                    positionSource = viewSource.tag as Int

                    val cardSource = adapterSource.getList()[positionSource]
                    val cardListSource: MutableList<CardsUI> = adapterSource.getList()

                    cardListSource.removeAt(positionSource)
                    adapterSource.notifyItemRemoved(positionSource)
                    cardListSource.add(positionSource, CardsUI("", null, null, true))
                    adapterSource.notifyItemInserted(positionSource)
                    adapterSource.updateList(cardListSource)

                    val adapterTarget = target.adapter as PlayingCardsAnsweringAdapter
                    val cardListTarget: MutableList<CardsUI> = adapterTarget.getList()

                    if (positionTarget >= 0 && cardListTarget[positionTarget].emptySpace == true) {
                        cardListTarget.removeAt(positionTarget)
                        adapterTarget.notifyItemRemoved(positionTarget)
                        cardListTarget.add(positionTarget, cardSource)
                        adapterTarget.notifyItemInserted(positionTarget)
                        adapterTarget.updateList(cardListTarget)
                    } else {
                        showMistake()
                        cardListSource.removeAt(positionSource)
                        adapterSource.notifyItemRemoved(positionSource)
                        cardListSource.add(positionSource, cardSource)
                        adapterSource.notifyItemInserted(positionSource)
                        adapterSource.updateList(cardListSource)
                    }

                    v.visibility = View.VISIBLE
                    if (source.id == R.id.rv_cards_answering
                        && adapterSource.itemCount < 1
                    ) {
                        listener.setEmptyList(true)
                    }
                    if (v.id == R.id.empty_list_text_view_1) {
                        listener.setEmptyList(false)
                    }
                }
            }
            DragEvent.ACTION_DRAG_ENDED -> {}
            else -> {}
        }
        if (!isDropped) {
            val vw = event.localState as View
            vw.visibility = View.VISIBLE
        }
        return true
    }
}