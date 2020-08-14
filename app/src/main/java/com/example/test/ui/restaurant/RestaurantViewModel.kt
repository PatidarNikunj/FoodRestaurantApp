package com.example.test.ui.restaurant

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.example.test.data.DataManager
import com.example.test.ui.MainViewModel
import com.example.test.ui.base.BaseViewModel
import com.example.test.utils.NetworkUtils
import com.example.test.utils.rx.SchedulerProvider

/**
 * Created by Android.Developer.
 * @version 1.0
 */

class RestaurantViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<RestaurantNavigator>(dataManager, schedulerProvider), Observable {
    lateinit var mainViewModel: MainViewModel

/*
    private var pendingInvoices = ObservableField<String>()

    @Bindable
    fun getPendingInvoices(): ObservableField<String> {
        return pendingInvoices
    }


    internal fun setPendingInvoices(_pendingInvoices: ObservableField<String>) {
        pendingInvoices = _pendingInvoices
        registry.notifyChange(this, BR.pendingInvoices)
    }
*/

    internal fun fetchRestaurants(lat: Double = 23.0750929, lon: Double = 72.5117242) {
        callback?.onShowLoader()
        compositeDisposable.add(
            dataManager.doServerGetRestaurant(
                lat,
                lon,
                50,
                "real_distance",
                "asc"
            ).subscribeOn(
                schedulerProvider.io()
            )
                .observeOn(schedulerProvider.ui()).subscribe({ response ->
                    callback?.onDismissLoader()
                    if (response != null) {
                        dataManager.user
                        navigator?.onRestaurantResponseReceived(response)
                    } else {
                        navigator?.onNoResponseReceived()
                    }
                }, { throwable ->
                    callback?.onDismissLoader()
                    throwable.printStackTrace()
                    navigator?.onHandleError(throwable.localizedMessage ?: "")
                })
        )
    }

    internal fun fetchLocation(query: String = "sola, ahmedabad") {
        callback?.onShowLoader()
        compositeDisposable.add(
            dataManager.doServerGetLocations(
                query = query, count = 10
            ).subscribeOn(
                schedulerProvider.io()
            )
                .observeOn(schedulerProvider.ui()).subscribe({ response ->
                    callback?.onDismissLoader()
                    if (response != null) {
                        navigator?.onLocationResponseReceived(response)
                    } else {
                        navigator?.onNoResponseReceived()
                    }
                }, { throwable ->
                    callback?.onDismissLoader()
                    throwable.printStackTrace()
                    navigator?.onHandleError(throwable.localizedMessage ?: "")
                })
        )
    }

    private val registry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        registry.remove(callback)
    }
}
