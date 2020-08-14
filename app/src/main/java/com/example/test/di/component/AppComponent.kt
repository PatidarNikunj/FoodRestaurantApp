package com.example.test.di.component

import android.app.Application
import com.example.test.MyApplication
import com.example.test.di.builder.ActivityBuilder
import com.example.test.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Created by Android.Developer.
 * @version 1.0
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent

    }

    fun inject(app: MyApplication)

}
