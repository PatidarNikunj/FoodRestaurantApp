package com.example.test.ui.base

import android.annotation.TargetApi
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.test.R
import com.example.test.utils.LocaleHelper
import com.example.test.utils.NetworkUtils
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.content_main.*


/**
 * Created by Android.Developer.
 * @version 1.0
 */

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(),
    BaseFragment.Callback,
    BaseViewModel.RefreshTokenCallback {

    protected var viewDataBinding: T? = null

    private var mViewModel: V? = null

    private var initialLocale: String? = null

    val isNetworkConnected: Boolean
        get() = NetworkUtils.isNetworkConnected(applicationContext)

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

    val isLandscape: Boolean
        get() = resources.configuration.orientation == ORIENTATION_LANDSCAPE

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

        performDependencyInjection()
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        initializeProgressDialog(this)
        viewModel.setOnRefreshTokenCallbackListener(this)
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase))
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetached(tag: String) {

    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun openActivityOnTokenExpire() {
//        startActivity(LoginActivity.getStartIntent(this))
//        finish()
    }

    fun showInternetConnectionErrorDialog() {
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_LONG).show()
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        nav_host_fragment.childFragmentManager.fragments[0].onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }
}