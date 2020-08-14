package com.example.test.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Android.Developer.
 * @version 1.0
 */

class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        if (parent.getChildAdapterPosition(view) != parent.getAdapter()!!.getItemCount() - 1) {
        /*if (parent.getChildAdapterPosition(view) % 2 == 0) {
            outRect.right = space / 2
        } else {
            outRect.left = space / 2
        }*/
//        }
//        outRect.left = space * 2
//        outRect.right = space * 2
        if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
            outRect.bottom = (space * 0.7).toInt()
        }

    }
}