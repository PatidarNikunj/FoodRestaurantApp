package com.example.test.data

import com.example.test.data.local.db.DbHelper
import com.example.test.data.local.pref.PreferencesHelper
import com.example.test.data.remote.ApiHelper

/**
 * Created by Android.Developer.
 * DataManager contains methods related to data management of local-db, preferences and APIs.
 * @version 1.0
 */

interface DataManager : DbHelper, PreferencesHelper, ApiHelper {

    fun updateApiHeader(accessToken: String?)

    enum class ErrorState {
        NO_DATA,
        API_FAIL,
        NETWORK_ERROR
    }
}
