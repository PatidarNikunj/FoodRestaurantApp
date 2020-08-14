package com.example.test.ui.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.example.test.data.DataManager
import com.example.test.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Android.Developer.
 * @version 1.0
 */

abstract class BaseViewModel<N> protected constructor(
    val dataManager: DataManager,
    protected val schedulerProvider: SchedulerProvider
) : ViewModel() {

    var navigator: N? = null

    private val mIsLoading = ObservableBoolean(false)

    private var mCompositeDisposable: CompositeDisposable? = null

    protected val compositeDisposable: CompositeDisposable
        get() {
            if (mCompositeDisposable == null) mCompositeDisposable = CompositeDisposable()
            return mCompositeDisposable as CompositeDisposable
        }

    fun onViewCreated() {
        this.mCompositeDisposable = CompositeDisposable()
    }

    fun onDestroyView() {
        mCompositeDisposable!!.dispose()
    }

    fun getIsLoading(): ObservableBoolean {
        return mIsLoading
    }

    fun setIsLoading(isLoading: Boolean) {
        mIsLoading.set(isLoading)
    }

    var callback: RefreshTokenCallback? = null

    fun setOnRefreshTokenCallbackListener(callback: RefreshTokenCallback) {
        this.callback = callback
    }

    interface RefreshTokenCallback {
        fun onShowLoader()
        fun onDismissLoader()
    }

}

