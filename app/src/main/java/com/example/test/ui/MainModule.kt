package com.example.test.ui

import com.example.test.data.DataManager
import com.example.test.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Module
class MainModule {

    @Provides
    internal fun provideMainViewModel(
            dataManager: DataManager,
            schedulerProvider: SchedulerProvider
    ): MainViewModel {
        return MainViewModel(dataManager, schedulerProvider)
    }

}