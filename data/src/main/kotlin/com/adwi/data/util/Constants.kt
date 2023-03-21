package com.adwi.data.util

import com.adwi.data.BuildConfig

object Constants {

    // Database
    const val WALLPAPER_DATABASE = "wallpaper_database"

    // PexApi
    const val BASE_URL = "https://api.pexels.com/v1/"
    const val API_KEY = "563492ad6f91700001000001909d7c0d3da24d7cbdf594f195f670c4"
    const val AUTHORIZATION = "Authorization"

    const val DAILY_PAGE_SIZE = 31
    const val CURATED_PAGE_SIZE = 20
    const val PAGING_PAGE_SIZE = 20

    const val DEFAULT_CATEGORY = "Curated"
    const val DEFAULT_DAILY_CATEGORY = "Purple Orange"

    const val REFRESH_DATA_EVERY = 5L

    // Pager
    const val PAGING_SIZE = 20
    const val PAGING_MAX_SIZE = 200
}