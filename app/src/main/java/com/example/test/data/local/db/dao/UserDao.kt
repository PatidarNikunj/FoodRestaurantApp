package com.example.test.data.local.db.dao

import androidx.room.*
import com.example.test.data.local.db.entity.User

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun loadAll(): List<User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: List<Int>): List<User>

    @Query("SELECT * FROM user WHERE name LIKE :name LIMIT 1")
    fun findByName(name: String): User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<User>)

    @Delete
    fun delete(user: User)

}