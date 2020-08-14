package com.example.test.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.inject.Singleton

/**
 * Created by Android.Developer.
 * Header which will add while making network calls.
 * @version 1.0
 */


@Singleton
class ApiHeader(
    @field:Expose
    @field:SerializedName("user-key")
    var accessToken: String?
)
