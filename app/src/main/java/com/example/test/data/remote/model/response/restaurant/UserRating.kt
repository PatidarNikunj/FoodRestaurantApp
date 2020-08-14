package com.example.test.data.remote.model.response.restaurant

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class UserRating(
    val aggregate_rating: Double,
    val rating_text: String,
    val rating_color: String,
    @Embedded val ratings: Ratings,
    val votes: Int
)