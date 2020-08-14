package com.example.test.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test.data.local.db.dao.RestaurantDao
import com.example.test.data.local.db.dao.UserDao
import com.example.test.data.local.db.entity.User
import com.example.test.data.remote.model.response.restaurant.Restaurant

/**
 * Created by Android.Developer.
 * @version 1.0
 */

@Database(entities = [User::class, Restaurant::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
//    abstract fun restaurantDao(): RestaurantDao

}
