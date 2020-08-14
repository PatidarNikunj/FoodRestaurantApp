package com.example.test.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.test.BR
import com.example.test.R
import com.example.test.ui.MainActivity
import com.example.test.ui.base.BaseActivity
import javax.inject.Inject

/**
 * Created by Android.Developer.
 * @version 1.0
 */


class SplashActivity :
    BaseActivity<com.example.test.databinding.ActivitySplashBinding, SplashViewModel>(),
    SplashNavigator {

    @Inject
    override lateinit var viewModel: SplashViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        viewModel.startSeeding()
    }

    override fun openLanguageSelectionActivity() {

    }

    override fun openMainActivity() {
        //Auto-login
        val intent = MainActivity.getStartIntent(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun openLoginActivity() {
        //Auto-login
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

}
