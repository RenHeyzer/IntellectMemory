package com.geektech.intellect_memort.common.utils.views

import android.content.Context
import android.text.TextWatcher
import android.util.AttributeSet


class RecyclerEditText : androidx.appcompat.widget.AppCompatEditText {

    private var mListeners: ArrayList<TextWatcher>? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context,
        attrs,
        defStyleAttr)

    override fun addTextChangedListener(watcher: TextWatcher) {
        if (mListeners == null) {
            mListeners = ArrayList()
        }
        mListeners!!.add(watcher)
        super.addTextChangedListener(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher) {
        if (mListeners != null) {
            val i = mListeners?.indexOf(watcher)
            if (i != null) {
                if (i >= 0) {
                    mListeners?.removeAt(i)
                }
            }
        }
        super.removeTextChangedListener(watcher)
    }

    fun clearTextChangedListeners() {
        if (mListeners != null) {
            for (watcher in mListeners!!) {
                super.removeTextChangedListener(watcher)
            }
            mListeners!!.clear()
            mListeners = null
        }
    }
}