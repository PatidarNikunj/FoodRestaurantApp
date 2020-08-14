package com.example.test.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.test.data.local.db.DbHelper
import com.example.test.data.local.db.entity.User
import com.example.test.data.local.pref.PreferencesHelper
import com.example.test.data.remote.ApiHeader
import com.example.test.data.remote.ApiHelper
import com.example.test.data.remote.model.response.location.LocationDataResponse
import com.example.test.data.remote.model.response.restaurant.RestaurantDataResponse
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Android.Developer.
 * AppDataManager manages operations of local-db, preferences and API Calls.
 * @version 1.0
 */

@Singleton
class AppDataManager @Inject
constructor(
    context: Context,
    private val mDbHelper: DbHelper,
    private val mPreferencesHelper: PreferencesHelper,
    private val mApiHelper: ApiHelper
) : DataManager {

    override fun doServerGetLocations(
        query: String,
        lat: Double,
        lon: Double,
        count: Int
    ): Observable<LocationDataResponse> {
        return mApiHelper.doServerGetLocations(
            query, lat, lon, count
        )
    }

    override fun doServerGetRestaurant(
        lat: Double,
        lon: Double,
        radius: Int,
        sort: String,
        order: String
    ): Observable<RestaurantDataResponse> {
        return mApiHelper.doServerGetRestaurant(
            lat, lon, radius, sort, order
        )
    }

    override fun updateApiHeader(accessToken: String?) {
        mApiHelper.apiHeader.accessToken = accessToken ?: "7743aea88c2d4f753465c610ef1cf5a8"
    }

    // DB HELPER METHODS
    override val user: LiveData<User>?
        get() = mDbHelper.user

//    override fun insertUser(user: User): Observable<Boolean> {
//        TODO("Not yet implemented")
//    }
//
//    override val restaurants: LiveData<Restaurant>?
//        get() = mDbHelper.restaurants
//
//    override fun insertRestaurant(restaurant: Restaurant): Observable<Boolean> {
//        return mDbHelper.insertRestaurant(restaurant)
//    }
//
//    override fun removeRestaurant(restaurant: Restaurant): Observable<Boolean> {
//        return mDbHelper.insertRestaurant(restaurant)
//    }


    // PREF HELPER METHODS
    override var accessToken: String
        get() = mPreferencesHelper.accessToken
        set(value) {
            mPreferencesHelper.accessToken = value
        }

    override val apiHeader: ApiHeader
        get() = mApiHelper.apiHeader


}