package com.example.test.data.remote

import com.example.test.data.remote.model.response.location.LocationDataResponse
import com.example.test.data.remote.model.response.restaurant.RestaurantDataResponse
import com.rx2androidnetworking.Rx2AndroidNetworking
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Android.Developer.
 * AppApiHelper contains networks calls implementation using RxNetworking lib.
 * @version 1.0
 */

@Singleton
class AppApiHelper @Inject
constructor(
    override val apiHeader: ApiHeader
) : ApiHelper {

    override fun doServerGetLocations(
        query: String,
        lat: Double,
        lon: Double,
        count: Int
    ): Observable<LocationDataResponse> {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_SERVER_LOCATIONS)
            .addQueryParameter("query", query)
            .addQueryParameter("lat", lat.toString())
            .addQueryParameter("lon", lon.toString())
            .addQueryParameter("count", count.toString())
            .addHeaders(apiHeader)
            .build()
            .getObjectObservable(LocationDataResponse::class.java)
    }

    override fun doServerGetRestaurant(
        lat: Double,
        lon: Double,
        radius: Int,
        sort: String,
        order: String
    ): Observable<RestaurantDataResponse> {
        return Rx2AndroidNetworking.get(ApiEndPoint.ENDPOINT_SERVER_RESTAURANT)
            .addQueryParameter("lat", lat.toString())
            .addQueryParameter("lon", lon.toString())
            .addQueryParameter("radius", radius.toString())
            .addQueryParameter("sort", sort)
            .addQueryParameter("order", order)
            .addHeaders(apiHeader)
            .build()
            .getObjectObservable(RestaurantDataResponse::class.java)
    }

}
