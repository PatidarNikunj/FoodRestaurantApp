package com.example.test.ui.favourite_restaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data.DataManager
import com.example.test.data.remote.model.response.restaurant.Restaurants

/**
 * Created by Android.Developer.
 * @version 1.0
 */

class FavouriteRestaurantCardAdapter(
    private val context: Context,
    private val restaurantList: List<Restaurants>,
    val dataManager: DataManager,
    val onClickListener: View.OnClickListener
) : RecyclerView.Adapter<FavouriteRestaurantCardAdapter.MyViewHolder>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurant_card, parent, false) as View
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        updateDashboardItemChart(holder, position)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = restaurantList.size

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private fun updateDashboardItemChart(holder: MyViewHolder, position: Int) {
    }

}