package com.adwi.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter

@ExperimentalCoilApi
@Composable
fun PexCoilImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    placeholder: Int = R.drawable.image_placeholder,
    contentDescription: String = "",
    isSquare: Boolean = false,
    wallpaperId: Int? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    if (isSquare) modifier.size(200.dp)
    Image(
        painter = rememberImagePainter(
            data = imageUrl,
            builder = {
                wallpaperId?.let {
                    placeholderMemoryCacheKey(wallpaperId.toString())
                }
                error(placeholder)
            }
        ),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
    )
}