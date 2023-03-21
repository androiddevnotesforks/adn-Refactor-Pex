package com.adwi.pexwallpapers

import android.app.Application
import android.app.NotificationManager
import androidx.hilt.work.HiltWorkerFactory
import androidx.paging.ExperimentalPagingApi
import androidx.work.Configuration
import com.adwi.tool_automation.util.createNotificationChannel
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import javax.inject.Inject


@ExperimentalCoroutinesApi
@ExperimentalPagingApi
@HiltAndroidApp
class PexApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        // Timber Logger
        val formatStrategy: FormatStrategy = PrettyFormatStrategy.newBuilder()
            .showThreadInfo(true) // (Optional) Whether to show thread info or not. Default true
            .methodCount(1) // (Optional) How many method line to show. Default 2
            .methodOffset(5) // Set methodOffset to 5 in order to hide internal method calls
            .tag("") // To replace the default PRETTY_LOGGER tag with a dash (-).
            .build()

        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree() {

            override fun log(
                priority: Int,
                tag: String?,
                message: String,
                t: Throwable?,
            ) {
                Logger.log(priority, "-GLOBALTAG--$tag", message, t)
            }
        })

        // Usage
        Timber.d("onCreate: Inside Application!")
        // end Timber Logger

        createNotificationChannel(notificationManager)
    }
}