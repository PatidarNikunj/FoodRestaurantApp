package com.example.test.data.remote

import com.example.test.data.remote.model.response.location.LocationDataResponse
import com.example.test.data.remote.model.response.restaurant.RestaurantDataResponse
import io.reactivex.Observable

/**
 * Created by Android.Developer.
 * ApiHelper contains networks call's declaration with Request and Response data types.
 * @version 1.0
 */

interface ApiHelper {

    val apiHeader: ApiHeader

    fun doServerGetLocations(
        query: String = "aurum,sola, ahmedabad",
        lat: Double = 0.0,
        lon: Double = 0.0,
        count: Int = 10
    ): Observable<LocationDataResponse>

    fun doServerGetRestaurant(
        lat: Double = 0.0,
        lon: Double = 0.0,
        radius: Int = 50,
        sort: String = "real_distance",
        order: String = "asc"
    ): Observable<RestaurantDataResponse>
}