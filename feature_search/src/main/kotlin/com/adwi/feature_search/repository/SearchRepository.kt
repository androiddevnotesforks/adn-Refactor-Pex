package com.adwi.feature_search.repository

import androidx.paging.PagingData
import com.adwi.data.database.domain.WallpaperEntity
import com.adwi.domain.Wallpaper
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearch(query: String): Flow<PagingData<WallpaperEntity>>

    suspend fun updateWallpaper(wallpaper: Wallpaper)
}