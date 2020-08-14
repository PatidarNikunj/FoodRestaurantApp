package com.example.test.data.remote.model.response.restaurant

import androidx.room.Entity

@Entity
data class Location(
    val address: String,
    val locality: String,
    val city: String,
    val city_id: Int,
    val latitude: Double,
    val longitude: Double,
    val zipcode: String,
    val country_id: Int,
    val locality_verbose: String
)