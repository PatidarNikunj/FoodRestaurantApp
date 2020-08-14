package com.example.test.ui.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Android.Developer.
 * @version 1.0
 */

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun onBind(position: Int)

}
