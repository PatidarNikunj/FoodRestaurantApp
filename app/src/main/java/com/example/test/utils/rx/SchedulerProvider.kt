package com.example.test.utils.rx

import io.reactivex.Scheduler

/**
 * Created by Android.Developer.
 * @version 1.0
 */

interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler

}
