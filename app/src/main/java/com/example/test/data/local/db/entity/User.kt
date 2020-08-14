package com.example.test.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Entity
class User(@field:PrimaryKey var id: Long?, var name: String) {

    @Ignore
    @ColumnInfo(name = "created_at")
    var createdAt: String? = null

    @Ignore
    @ColumnInfo(name = "updated_at")
    var updatedAt: String? = null
}