package com.example.test.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.test.BR
import com.example.test.R
import com.example.test.databinding.NavHeaderMainBinding
import com.example.test.ui.base.BaseActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject


/**
 * Created by Android.Developer.
 * @version 1.0
 */

class MainActivity :
    BaseActivity<com.example.test.databinding.ActivityMainBinding, MainViewModel>(),
    MainNavigator, HasSupportFragmentInjector, NavController.OnDestinationChangedListener {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.nav_restaurant
            )
        )
    }
    private lateinit var headerBinding: NavHeaderMainBinding

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    public override lateinit var viewModel: MainViewModel

    override val bindingVariable: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.navigator = this
        setUp()
    }

    override val layoutId: Int
        get() = R.layout.activity_main

    private fun setUp() {
        setSupportActionBar(toolbar)
        setupDataBinding()
        setupNavigation()
        setupViews()
//        setBadge()
    }

    private fun setupDataBinding() {
        headerBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.nav_header_main, null, false
        )
        headerBinding.clProfile.setOnClickListener {
//            navController.navigate(R.id.profileFragment)

        }
    }

    private fun setupNavigation() {
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)
        navController.addOnDestinationChangedListener(this)
    }

    private fun setupViews() {
//        navController.navigate(R.id.nav_home)
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment>? {
        return fragmentDispatchingAndroidInjector
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        supportActionBar?.title = ""
        titleToolbar.text = destination.label
    }

    override fun onBackPressed() {
        finish()
    }

}
