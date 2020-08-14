package com.example.test.ui.restaurant

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.data.DataManager
import com.example.test.data.remote.model.response.location.LocationSuggestions
import com.example.test.data.remote.model.response.restaurant.Restaurants
import com.squareup.picasso.Picasso

/**
 * Created by Android.Developer.
 * @version 1.0
 */

class RestaurantCardAdapter(
    private val context: Context,
    private val adapterDataList: List<Any>,
    val dataManager: DataManager,
    val onClickListener: (Any) -> Unit
//) : RecyclerView.Adapter<RestaurantCardAdapter.MyViewHolder>() {
) : RecyclerView.Adapter<RestaurantCardAdapter.BaseViewHolder<*>>() {

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<*> {
        // create a new view
        return when (viewType) {
            TYPE_RESTAURANT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_restaurant_card, parent, false) as View
                RestaurantViewHolder(view)
            }
            TYPE_LOCATION -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_location, parent, false) as View
                LocationViewHolder(view)
            }
            else -> {
                throw IllegalArgumentException("Invalid view type")
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val element = adapterDataList[position]
        when (holder) {
            is RestaurantViewHolder -> holder.bind(element as Restaurants)
            is LocationViewHolder -> holder.bind(element as LocationSuggestions)
            else -> throw IllegalArgumentException()
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int = adapterDataList.size

    override fun getItemViewType(position: Int): Int {
        return when (adapterDataList[position]) {
            is Restaurants -> TYPE_RESTAURANT
            is LocationSuggestions -> TYPE_LOCATION
            else -> throw IllegalArgumentException("Invalid type of data " + position)
        }
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    inner class RestaurantViewHolder(itemView: View) : BaseViewHolder<Restaurants>(itemView) {
        val ivRestaurant: ImageView = itemView.findViewById(R.id.iv_restaurant)
        val ivFavourite: ImageView = itemView.findViewById(R.id.iv_favourite)
        val tvRestaurantName: TextView = itemView.findViewById(R.id.tv_restaurant_name)
        val tvRestaurantAddress: TextView = itemView.findViewById(R.id.tv_restaurant_address)
        val tvAverageCost: TextView = itemView.findViewById(R.id.tv_average_cost)
        val tvRestaurantVotes: TextView = itemView.findViewById(R.id.tv_restaurant_votes)
        val tvRatings: TextView = itemView.findViewById(R.id.tv_ratings)
        override fun bind(item: Restaurants) {
            updateAdapterItems(this, item)
            ivFavourite.setOnClickListener { onClickListener(item) }
        }
    }

    inner class LocationViewHolder(itemView: View) : BaseViewHolder<LocationSuggestions>(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvCity: TextView = itemView.findViewById(R.id.tv_city)
        override fun bind(item: LocationSuggestions) {
            updateAdapterItems(this, item)
            itemView.setOnClickListener { onClickListener(item) }
        }
    }

    private fun updateAdapterItems(mHolder: BaseViewHolder<*>, dataItem: Any) {
        when (dataItem) {
            is Restaurants -> {
                val restaurant = dataItem.restaurant
                val holder = mHolder as RestaurantViewHolder
                val thumb = if (restaurant.thumb.isNotEmpty()) {
                    restaurant.thumb
                } else {
                    "https://akm-img-a-in.tosshub.com/sites/btmt/images/stories/zomato-fact-sheet_505_052417055850_111517063712.jpg?size=1200:675"
                }
                Picasso.get().load(thumb).into(holder.ivRestaurant)
                holder.ivFavourite
                holder.tvRestaurantName.text = restaurant.name
                holder.tvRestaurantAddress.text =
                    "${restaurant.location.address}, ${restaurant.location.city}"
                holder.tvAverageCost.text =
                    "Average cost for 2 person: Rs. ${restaurant.average_cost_for_two}"
                holder.tvRestaurantVotes.text = "${restaurant.user_rating.votes} Votes"
                holder.tvRatings.text = "${restaurant.user_rating.aggregate_rating} / 5.0"
                holder.tvRatings.backgroundTintList =
                    ColorStateList.valueOf(Color.parseColor("#${restaurant.user_rating.rating_color}"))
            }
            is LocationSuggestions -> {
                val location = dataItem
                val holder = mHolder as LocationViewHolder
                holder.tvTitle.text = location.title
                holder.tvCity.text = location.city_name

            }
            else -> throw IllegalArgumentException("Invalid type of data ")
        }
    }


    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    companion object {
        private const val TYPE_RESTAURANT = 0
        private const val TYPE_LOCATION = 1
    }
}