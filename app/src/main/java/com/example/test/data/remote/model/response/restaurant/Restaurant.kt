package com.example.test.data.remote.model.response.restaurant

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
// You can give a alias of your choice by uncommenting below line code,
// if not provided it will take class name as table name
// (tableName = "favourite_restaurant")
data class Restaurant(
//    val r: R,
    val apiapikey: String,
    @field:PrimaryKey val id: Int,
    val name: String,
    val url: String,
    @Embedded val location: Location,
    val switch_to_order_menu: Int,
    val cuisines: String,
    val timings: String,
    val average_cost_for_two: Int,
    val price_range: Int,
    val currency: String,
//    val highlights: List<String>,
//    val offers: List<String>,
    val opentable_support: Int,
    val is_zomato_book_res: Int,
    val mezzo_provider: String,
    val is_book_form_web_view: Int,
    val book_form_web_view_url: String,
    val book_again_url: String,
    val thumb: String,
    @Embedded val user_rating: UserRating,
    val all_reviews_count: Int,
    val photos_url: String,
    val photo_count: Int,
    val menu_url: String,
    val featured_image: String,
    val has_online_delivery: Int,
    val is_delivering_now: Int,
    val store_type: String,
    val include_bogo_offers: Boolean,
    val deeplink: String,
    val is_table_reservation_supported: Int,
    val has_table_booking: Int,
    val events_url: String,
    val phone_numbers: String,
//    val all_reviews: AllReviews,
//    val establishment: List<String>,
//    val establishment_types: List<String>,
    val isFavourite: Boolean = false
)