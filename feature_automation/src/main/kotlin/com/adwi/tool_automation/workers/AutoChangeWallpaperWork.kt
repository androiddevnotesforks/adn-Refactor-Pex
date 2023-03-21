package com.adwi.tool_automation.workers

import android.app.NotificationManager
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.paging.ExperimentalPagingApi
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker.Result.failure
import androidx.work.ListenableWorker.Result.success
import androidx.work.WorkerParameters
import com.adrianwitaszak.tool_image.imagemanager.ImageManager
import com.adrianwitaszak.tool_image.wallpapersetter.WallpaperSetter
import com.adwi.feature_settings.data.database.SettingsDao
import com.adwi.tool_automation.automation.AutomationManager
import com.adwi.tool_automation.util.Constants.WALLPAPER_ID
import com.adwi.tool_automation.util.Constants.WALLPAPER_IMAGE_URL
import com.adwi.tool_automation.util.sendAutoChangeWallpaperNotification
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import timber.log.Timber

private const val TAG = "AutoChangeWallpaperWork"

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltWorker
class AutoChangeWallpaperWork @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val automationManager: AutomationManager,
    private val imageManager: ImageManager,
    private val wallpaperSetter: WallpaperSetter,
    private val settingsDao: SettingsDao,
    private val notificationManager: NotificationManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        Timber.tag(TAG).d("doWork")
        return try {
            val wallpaperId = inputData.getInt(WALLPAPER_ID, 0)
            val wallpaperImageUrl = inputData.getString(WALLPAPER_IMAGE_URL)
            val settings = settingsDao.getSettings().first()

            if (!settings.autoChangeOverWiFi) {
                automationManager.backupCurrentWallpaper(wallpaperId)

                val bitmap = wallpaperImageUrl?.let { imageUrl ->
                    imageManager.getBitmapFromRemote(imageUrl)
                }

                bitmap?.let {
                    wallpaperSetter.setWallpaper(
                        bitmap = it,
                        home = settings.autoHome,
                        lock = settings.autoLock
                    )

                    if (settings.autoChangeWallpaper) {
                        context.sendAutoChangeWallpaperNotification(
                            notificationManager = notificationManager,
                            bitmap = it,
                            wallpaperId = wallpaperId
                        )
                        Timber.tag(TAG).d("Sending notification id = $wallpaperId")
                    } else {
                        Timber.tag(TAG).d("autoChangeWallpaper setting is off")
                    }
                    success()
                }
            } else {
                Timber.tag(TAG).d(
                    "AutoChangeWallpaperWork - autoChangeOverWiFi is true"
                )
            }
            Timber.tag(TAG).d("AutoChangeWallpaperWork - success")
            success()
        } catch (ex: Exception) {
            Timber.tag(TAG).d(ex.toString())
            failure()
        }
    }
}