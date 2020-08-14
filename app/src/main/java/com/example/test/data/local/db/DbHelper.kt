package com.example.test.data.local.db

import androidx.lifecycle.LiveData
import com.example.test.data.local.db.entity.User
import com.example.test.data.remote.model.response.restaurant.Restaurant
import io.reactivex.Observable

/**
 * Created by Android.Developer.
 * @version 1.0
 */
interface DbHelper {

    val user: LiveData<User>?
//    fun insertUser(user: User): Observable<Boolean>
//
//    val restaurants: LiveData<Restaurant>?
//    fun insertRestaurant(restaurant: Restaurant): Observable<Boolean>
//    fun removeRestaurant(restaurant: Restaurant): Observable<Boolean>


}
