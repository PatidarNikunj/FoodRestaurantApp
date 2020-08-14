package com.example.test.ui.restaurant

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Module
abstract class RestaurantProvider {

    @ContributesAndroidInjector(modules = arrayOf(RestaurantModule::class))
    internal abstract fun provideRestaurantFragmentFactory(): RestaurantFragment
}
