package com.example.test.data.local.db

import androidx.lifecycle.LiveData
import com.example.test.data.local.db.entity.User
import com.example.test.data.remote.model.response.restaurant.Restaurant
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Singleton
class AppDbHelper
@Inject
constructor(private val mAppDatabase: AppDatabase) : DbHelper {

    override val user: LiveData<User>?
        get() = null

//    override fun insertUser(user: User): Observable<Boolean> {
//        TODO("Not yet implemented")
//    }
//
//
//    override val restaurants: LiveData<Restaurant>?
//        get() = mAppDatabase.restaurantDao().loadAllFavourite()
//
//    override fun insertRestaurant(restaurant: Restaurant): Observable<Boolean> {
//        return mAppDatabase.restaurantDao().insert(restaurant)
//    }
//
//    override fun removeRestaurant(restaurant: Restaurant): Observable<Boolean> {
//        return mAppDatabase.restaurantDao().delete(restaurant)
//    }

    /* val searches: Observable<LiveData<List<Search>>>
         get() = Observable.fromCallable({ mAppDatabase.searchDao().loadAll() })

     val recentSearches: Observable<List<Search>>
         get() = Observable.fromCallable({ mAppDatabase.searchDao().loadRecentSearches() })

     fun insertUser(user: User): Observable<Boolean> {
         return Observable.fromCallable({
             mAppDatabase.userDao().insert(user)
             true
         })
     }*/
}