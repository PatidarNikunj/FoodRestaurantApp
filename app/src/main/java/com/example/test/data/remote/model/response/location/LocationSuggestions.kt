package com.example.test.data.remote.model.response.location

data class LocationSuggestions(
    val entity_type: String?,
    val entity_id: Int?,
    val title: String?,
    val latitude: Double?,
    val longitude: Double?,
    val city_id: Int?,
    val city_name: String?,
    val country_id: Int?,
    val country_name: String?
)