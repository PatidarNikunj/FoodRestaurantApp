package com.example.test.data.remote.model.response.restaurant

import androidx.room.Entity

@Entity
data class BgColor(
    val type: String,
    val tint: Int
)