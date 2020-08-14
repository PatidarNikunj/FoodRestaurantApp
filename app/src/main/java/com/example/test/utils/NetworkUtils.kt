package com.example.test.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Android.Developer.
 * @version 1.0
 */


object NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}// This class is not publicly instantiable
