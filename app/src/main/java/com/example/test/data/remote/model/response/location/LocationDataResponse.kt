package com.example.test.data.remote.model.response.location

data class LocationDataResponse(
    val location_suggestions: List<LocationSuggestions>,
    val status: String,
    val has_more: Int,
    val has_total: Int,
    val user_has_addresses: Boolean
)