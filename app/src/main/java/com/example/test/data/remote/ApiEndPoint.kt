package com.example.test.data.remote

import com.example.test.BuildConfig

/**
 * Created by Android.Developer.
 * URL endpoints and image dir URLs which use to make network calls.
 * @version 1.0
 */

object ApiEndPoint {

    internal const val ENDPOINT_SERVER_LOCATIONS = BuildConfig.BASE_URL + "locations"

    internal const val ENDPOINT_SERVER_RESTAURANT = BuildConfig.BASE_URL + "search"

}
