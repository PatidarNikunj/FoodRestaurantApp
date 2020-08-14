package com.example.test.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.test.data.remote.model.response.restaurant.Restaurant
import io.reactivex.Observable

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurant")
    fun loadAll(): List<Restaurant>

    @Query("SELECT * FROM restaurant WHERE isFavourite = 1")
    fun loadAllFavourite(): LiveData<Restaurant>

    @Query("SELECT * FROM restaurant WHERE id IN (:ids)")
    fun loadAllByIds(ids: List<Int>): LiveData<List<Restaurant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(restaurant: Restaurant): Observable<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(restaurants: List<Restaurant>): Observable<Boolean>

    @Delete
    fun delete(restaurant: Restaurant): Observable<Boolean>

}