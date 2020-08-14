package com.example.test.ui.favourite_restaurant

import com.example.test.data.remote.model.response.restaurant.RestaurantDataResponse

/**
 * Created by Android.Developer.
 * @version 1.0
 */
interface FavouriteRestaurantNavigator {
    fun onResponseReceived(restaurantDataResponse: RestaurantDataResponse)
    fun onNoResponseReceived()
    fun onHandleError(error: String)
}