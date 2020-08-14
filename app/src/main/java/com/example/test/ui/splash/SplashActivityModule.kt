package com.example.test.ui.splash

import com.example.test.data.DataManager
import com.example.test.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Module
class SplashActivityModule {

    @Provides
    internal fun provideSplashViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): SplashViewModel {
        return SplashViewModel(dataManager, schedulerProvider)
    }

}
