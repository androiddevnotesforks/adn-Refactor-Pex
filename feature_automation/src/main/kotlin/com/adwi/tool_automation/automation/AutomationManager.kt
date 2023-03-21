package com.adwi.tool_automation.automation

import androidx.work.Data
import com.adwi.domain.Wallpaper

interface AutomationManager {

    fun startAutoChangeWallpaperWork(delay: Long, favorites: List<Wallpaper>)

    fun workCreateAutoChangeWallpaperWork(
        workName: String,
        wallpaper: Wallpaper,
        repeatInterval: Long,
        initialDelay: Long
    )

    fun cancelAutoChangeWorks()

    fun backupCurrentWallpaper(wallpaperId: Int)

    fun createDownloadWallpaperWork(
        wallpaper: Wallpaper,
        downloadWallpaperOverWiFi: Boolean
    )

    fun createWorkData(wallpaperId: Int): Data
}