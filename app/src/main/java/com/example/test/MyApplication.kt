package com.example.test

import android.app.Activity
import android.app.Application
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.example.test.di.component.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Android.Developer.
 * @version 1.0
 */

class MyApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)


        if (BuildConfig.DEBUG) {
            AndroidNetworking.initialize(applicationContext)
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
            AndroidNetworking.setConnectionQualityChangeListener { currentConnectionQuality, currentBandwidth ->
                Log.e(
                    TAG,
                    "onChange: currentConnectionQuality : $currentConnectionQuality currentBandwidth : $currentBandwidth"
                )
            }
        }
//        FirebaseMessaging.getInstance().isAutoInitEnabled = true
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity>? {
        return activityDispatchingAndroidInjector
    }

    companion object {
        private val TAG = MyApplication::class.java.simpleName
    }

}
