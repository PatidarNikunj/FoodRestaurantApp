package com.example.test.ui.splash

import android.os.Handler
import com.example.test.data.DataManager
import com.example.test.ui.base.BaseViewModel
import com.example.test.utils.AppConstants.SPLASH_DELAY
import com.example.test.utils.rx.SchedulerProvider

/**
 * Created by Android.Developer.
 * @version 1.0
 */

class SplashViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<SplashNavigator>(dataManager, schedulerProvider) {

    fun startSeeding() {
        Handler().postDelayed({ this.decideNextActivity() }, SPLASH_DELAY)
    }

    private fun decideNextActivity() {
//        If need to update accessToken/user-key then please use below code line to do so
//        dataManager.updateApiHeader("")
        navigator?.openMainActivity()
    }

}
