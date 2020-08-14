package com.example.test.ui.restaurant

import com.example.test.data.remote.model.response.location.LocationDataResponse
import com.example.test.data.remote.model.response.restaurant.RestaurantDataResponse

/**
 * Created by Android.Developer.
 * @version 1.0
 */
interface RestaurantNavigator {
    fun onRestaurantResponseReceived(restaurantDataResponse: RestaurantDataResponse)
    fun onLocationResponseReceived(locationDataResponse: LocationDataResponse)
    fun onNoResponseReceived()
    fun onHandleError(error: String)
}