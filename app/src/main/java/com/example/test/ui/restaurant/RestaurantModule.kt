package com.example.test.ui.restaurant

import com.example.test.data.DataManager
import com.example.test.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Module
class RestaurantModule {

    @Provides
    internal fun provideRestaurantViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): RestaurantViewModel {
        return RestaurantViewModel(dataManager, schedulerProvider)
    }
}
