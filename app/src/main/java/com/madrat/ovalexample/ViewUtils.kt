package com.madrat.ovalexample

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.Gravity
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.core.content.ContextCompat

object ViewUtils {
    @JvmStatic
    fun generateBackgroundWithShadow(
        context: Context,
        @ColorRes backgroundColor: Int,
        @DimenRes cornerRadius: Int,
        @ColorRes shadowColor: Int,
        @DimenRes elevation: Int,
        shadowGravity: Int
    ): Drawable {
        val cornerRadiusValue = context.resources.getDimension(cornerRadius)
        val elevationValue = context.resources.getDimension(elevation)
            .toInt()
        val shadowColorValue = ContextCompat.getColor(
            context,
            shadowColor
        )
        val backgroundColorValue = ContextCompat.getColor(
            context,
            backgroundColor
        )
        Paint().apply {
            style = Paint.Style.FILL
            setShadowLayer(
                cornerRadiusValue,
                0f,
                0f,
                0
            )
        }
        val shapeDrawablePadding = Rect().apply {
            left = elevationValue
            right = elevationValue
        }
        val yCoordinate: Int = when (shadowGravity) {
            Gravity.CENTER -> {
                with(shapeDrawablePadding) {
                    top = elevationValue
                    bottom = elevationValue
                }
                0
            }
            Gravity.TOP -> {
                with(shapeDrawablePadding) {
                    top = elevationValue * 2
                    bottom = elevationValue
                }
                -1 * elevationValue / 3
            }
            Gravity.BOTTOM -> {
                with(shapeDrawablePadding) {
                    top = elevationValue
                    bottom = elevationValue * 2
                }
                elevationValue / 3
            }
            else -> {
                with(shapeDrawablePadding) {
                    top = elevationValue
                    bottom = elevationValue * 2
                }
                elevationValue / 3
            }
        }
        val shapeDrawable = ShapeDrawable().apply {
            setPadding(shapeDrawablePadding)
            with(paint) {
                color = backgroundColorValue
                setShadowLayer(
                    cornerRadiusValue / 3f,
                    0f,
                    yCoordinate.toFloat(),
                    shadowColorValue
                )
                this
            }
            shape = OvalShape()
        }
        return LayerDrawable(arrayOf<Drawable>(shapeDrawable)).apply {
            setLayerInset(
                0,
                (elevationValue * 1.5).toInt(),
                elevationValue,
                (elevationValue * 1.5).toInt(),
                elevationValue * 2
            )
        }
    }
}