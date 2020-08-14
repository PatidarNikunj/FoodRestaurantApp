package com.example.test.data.remote.model.response.restaurant

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class Ratings(
    @Embedded  val title: Title,
    @Embedded val bg_color: BgColor
)