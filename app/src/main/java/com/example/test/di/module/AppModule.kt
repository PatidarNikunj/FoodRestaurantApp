package com.example.test.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.test.data.AppDataManager
import com.example.test.data.DataManager
import com.example.test.data.local.db.AppDatabase
import com.example.test.data.local.db.AppDbHelper
import com.example.test.data.local.db.DbHelper
import com.example.test.data.local.pref.AppPreferencesHelper
import com.example.test.data.local.pref.PreferencesHelper
import com.example.test.data.remote.ApiHeader
import com.example.test.data.remote.ApiHelper
import com.example.test.data.remote.AppApiHelper
import com.example.test.di.DatabaseInfo
import com.example.test.di.PreferenceInfo
import com.example.test.utils.AppConstants
import com.example.test.utils.rx.AppSchedulerProvider
import com.example.test.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Android.Developer.
 * @version 1.0
 */

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @PreferenceInfo
    fun providePreferenceName(): String {
        return AppConstants.PREF_NAME
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppConstants.DB_NAME
    }

    @Provides
    @Singleton
    fun providePreferencesHelper(appPreferencesHelper: AppPreferencesHelper): PreferencesHelper {
        return appPreferencesHelper
    }

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@DatabaseInfo dbName: String, context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, dbName)
            .build()

    }

    /* static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
        }
    };*/

    @Provides
    @Singleton
    fun provideDbHelper(appDbHelper: AppDbHelper): DbHelper {
        return appDbHelper
    }

    @Provides
    @Singleton
    fun provideApiHelper(appApiHelper: AppApiHelper): ApiHelper {
        return appApiHelper
    }

    @Provides
    @Singleton
    fun provideProtectedApiHeader(preferencesHelper: PreferencesHelper): ApiHeader {
        return ApiHeader(preferencesHelper.accessToken)
    }

}