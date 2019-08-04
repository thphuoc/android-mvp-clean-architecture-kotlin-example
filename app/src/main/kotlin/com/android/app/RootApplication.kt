package com.android.app

import androidx.multidex.MultiDexApplication
import com.android.app.di.appModule
import com.android.app.di.repositoryModule
import com.android.app.di.useCaseModule
import com.android.app.di.viewModelModule
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class RootApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig
            .initDefault(
                CalligraphyConfig.Builder()
                    .setFontAttrId(R.attr.fontPath)
                    .build()
            )

        initLogger()

        startKoin {
            androidContext(this@RootApplication)
            modules(arrayListOf(appModule, repositoryModule, useCaseModule, viewModelModule))
        }
    }

    private fun initLogger() {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return !BuildConfig.IS_PROD_FLAVOR
            }
        })
        Logger.addLogAdapter(DiskLogAdapter())
    }
}