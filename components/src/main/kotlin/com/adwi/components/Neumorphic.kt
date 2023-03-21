package com.adwi.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.adwi.components.theme.*

@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.neumorphicShadow(
    enabled: Boolean = true,
    isPressed: Boolean = false,
    topShadowLight: Color = PrimaryShadowLight,
    topShadowDark: Color = Neutral3,
    bottomShadowLight: Color = PrimaryShadowDark,
    bottomShadowDark: Color = Neutral5,
    alpha: Float = .99f,
    cornerRadius: Dp = 30.dp,
    shadowRadius: Dp = 10.dp,
    offset: Dp = (-10).dp,
) = composed {

    val isLightTheme = MaterialTheme.colors.isLight

    val colorTop = if (isLightTheme) topShadowLight else topShadowDark
    val colorBottom = if (isLightTheme) bottomShadowLight else bottomShadowDark
    val darkAlpha = if (isLightTheme) alpha else 0.1f

    val pressed = updateTransition(targetState = isPressed, label = "Shadow")
    val elevation by pressed.animateDp(label = "Offset") { state ->
        if (state) offset / 2 else offset
    }
    val shadowRadiusAnimated by pressed.animateDp(label = "Shadow") { state ->
        if (state) 0.dp else shadowRadius
    }
    val cornerRadiusAnimated by pressed.animateDp(label = "Corner") { state ->
        if (state) cornerRadius / 2 else cornerRadius
    }


    this.drawBehind {
        val transparentColor =
            android.graphics.Color.toArgb(colorTop.copy(alpha = 0.0f).value.toLong())
        val shadowColorTop =
            android.graphics.Color.toArgb(colorTop.copy(alpha = darkAlpha).value.toLong())
        val shadowColorBottom =
            android.graphics.Color.toArgb(colorBottom.copy(alpha = alpha).value.toLong())

        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor

            if (enabled) {
                frameworkPaint.setShadowLayer(
                    shadowRadiusAnimated.toPx(),
                    elevation.toPx(),
                    elevation.toPx(),
                    shadowColorTop
                )
                it.drawRoundRect(
                    0f,
                    0f,
                    this.size.width,
                    this.size.height,
                    cornerRadiusAnimated.toPx(),
                    cornerRadiusAnimated.toPx(),
                    paint
                )
            }
        }

        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            frameworkPaint.color = transparentColor

            if (enabled) {
                frameworkPaint.setShadowLayer(
                    shadowRadiusAnimated.toPx(),
                    (-elevation).toPx(),
                    (-elevation).toPx(),
                    shadowColorBottom
                )
                it.drawRoundRect(
                    0f,
                    0f,
                    this.size.width,
                    this.size.height,
                    cornerRadiusAnimated.toPx(),
                    cornerRadiusAnimated.toPx(),
                    paint
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light")
@Composable
private fun NeumorphicPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            Card(
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .neumorphicShadow(),
                content = {}
            )
        }
    }
}

@Preview(showBackground = true, name = "Dark")
@Composable
private fun NeumorphicPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            Card(
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .neumorphicShadow(),
                content = {}
            )
        }
    }
}