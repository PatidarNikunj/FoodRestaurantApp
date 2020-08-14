package com.example.test.ui.favourite_restaurant

import com.example.test.ui.favourite_restaurant.FavouriteRestaurantFragment
import com.example.test.ui.favourite_restaurant.FavouriteRestaurantModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Module
abstract class FavouriteRestaurantProvider {

    @ContributesAndroidInjector(modules = arrayOf(FavouriteRestaurantModule::class))
    internal abstract fun provideFavouriteRestaurantFragmentFactory(): FavouriteRestaurantFragment
}
