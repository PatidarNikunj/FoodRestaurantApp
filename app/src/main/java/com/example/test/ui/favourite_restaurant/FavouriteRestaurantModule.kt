package com.example.test.ui.favourite_restaurant

import com.example.test.data.DataManager
import com.example.test.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Module
class FavouriteRestaurantModule {

    @Provides
    internal fun provideFavouriteRestaurantViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): FavouriteRestaurantViewModel {
        return FavouriteRestaurantViewModel(dataManager, schedulerProvider)
    }
}
