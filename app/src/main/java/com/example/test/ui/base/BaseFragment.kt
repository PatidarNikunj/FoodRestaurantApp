package com.example.test.ui.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.test.R
import dagger.android.support.AndroidSupportInjection

/**
 * Created by Android.Developer.
 * @version 1.0
 */

abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel<*>> : Fragment(),
    BaseViewModel.RefreshTokenCallback {

    var baseActivity: BaseActivity<*, *>? = null
        private set
    private var viewDataBinding: T? = null

    protected fun getViewDataBinding(): T {
        return this.viewDataBinding!!
    }

    private var mViewModel: V? = null
    private var mRootView: View? = null

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    protected abstract val viewModel: V

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    protected abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = viewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
        mViewModel?.onViewCreated()
        initializeProgressDialog(activity)
        viewModel.setOnRefreshTokenCallbackListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
            activity?.onFragmentAttached()
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }


    override fun onDestroyView() {
        mViewModel!!.onDestroyView()
        super.onDestroyView()
    }

    fun hideKeyboard() {
        if (baseActivity != null) {
            baseActivity!!.hideKeyboard()
        }
    }

    fun openActivityOnTokenExpire() {
        if (baseActivity != null) {
            baseActivity!!.openActivityOnTokenExpire()
        }
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    private var dialogs: Dialog? = null

    fun initializeProgressDialog(context: Context?) {
        dialogs = Dialog(context!!).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            window!!.setDimAmount(0f)
            setContentView(R.layout.layout_progress)
        }
    }

    fun showDialog() {
        try {
            if (!dialogs!!.isShowing) {
                dialogs!!.show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun dismissDialog() {
        try {
            if (dialogs!!.isShowing) {
                dialogs!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onShowLoader() {
        showDialog()
    }

    override fun onDismissLoader() {
        dismissDialog()
    }


}