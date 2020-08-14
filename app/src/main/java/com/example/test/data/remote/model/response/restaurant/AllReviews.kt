package com.example.test.data.remote.model.response.restaurant

import androidx.room.Entity

@Entity
data class AllReviews(
    val reviews: List<Reviews>
)