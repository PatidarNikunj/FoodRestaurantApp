package com.example.test.data.local.pref

import android.content.Context
import android.content.SharedPreferences
import com.example.test.di.PreferenceInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


/**
 * Created by Android.Developer.
 * @version 1.0
 */
class AppPreferencesHelper @Inject
constructor(
    context: Context,
    @PreferenceInfo prefFileName: String
) : PreferencesHelper {

    fun saveArrayList(list: List<String>, key: String) {
        val gson = Gson()
        val json = gson.toJson(list)
        mPrefs.edit().putString(key, json).apply()
    }

    fun getArrayList(key: String): List<String> {
        val gson = Gson()
        val json = mPrefs.getString(key, "")
        val type = object : TypeToken<ArrayList<String>>() {
        }.type
        return gson.fromJson<List<String>>(json, type)
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override var accessToken: String
        get() = mPrefs.getString(PREF_KEY_ACCESS_TOKEN, "e2e0477fa151509e8d67139581017273")!!
        set(value) = mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, value).apply()

    companion object {
        private val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
    }

}