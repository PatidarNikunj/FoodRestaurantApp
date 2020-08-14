package com.example.test.di.builder

import com.example.test.ui.MainActivity
import com.example.test.ui.MainModule
import com.example.test.ui.favourite_restaurant.FavouriteRestaurantProvider
import com.example.test.ui.restaurant.RestaurantProvider
import com.example.test.ui.splash.SplashActivity
import com.example.test.ui.splash.SplashActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Android.Developer.
 * @version 1.0
 */

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector(
        modules =
        [MainModule::class, RestaurantProvider::class, FavouriteRestaurantProvider::class]
    )
    internal abstract fun bindMainActivity(): MainActivity

}
