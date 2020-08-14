package com.example.test.ui.favourite_restaurant

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import com.example.test.data.DataManager
import com.example.test.ui.MainViewModel
import com.example.test.ui.base.BaseViewModel
import com.example.test.utils.rx.SchedulerProvider

/**
 * Created by Android.Developer.
 * @version 1.0
 */

class FavouriteRestaurantViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<FavouriteRestaurantNavigator>(dataManager, schedulerProvider), Observable {
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

    internal fun fetchRestaurants() {
        callback?.onShowLoader()
        compositeDisposable.add(
            dataManager.doServerGetRestaurant(
                23.0750929,
                72.5117242,
                50,
                "real_distance",
                "asc"
            ).subscribeOn(
                schedulerProvider.io()
            )
                .observeOn(schedulerProvider.ui()).subscribe({ response ->
                    callback?.onDismissLoader()
                    if (response != null) {
                        navigator?.onResponseReceived(response)
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
