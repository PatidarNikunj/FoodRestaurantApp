package com.example.test.ui

import com.example.test.data.DataManager
import com.example.test.ui.base.BaseViewModel
import com.example.test.utils.rx.SchedulerProvider

/**
 * Created by Android.Developer.
 * @version 1.0
 */

class MainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<MainNavigator>(dataManager, schedulerProvider)
