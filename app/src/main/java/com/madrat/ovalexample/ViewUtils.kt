package com.madrat.ovalexample

import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import android.view.Gravity
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.LayerDrawable
import android.view.View

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
            setShadowLayer(cornerRadiusValue, 0f, 0f, 0)
        }
        
        val shapeDrawablePadding = Rect().apply {
            left = elevationValue - 4
            right = elevationValue - 4
        }
        
        val yCoordinate: Int
        when (shadowGravity) {
            Gravity.CENTER -> {
                shapeDrawablePadding.top = elevationValue
                shapeDrawablePadding.bottom = elevationValue
                yCoordinate = 0
            }
            Gravity.TOP -> {
                shapeDrawablePadding.top = elevationValue * 2
                shapeDrawablePadding.bottom = elevationValue
                yCoordinate = -1 * elevationValue / 3
            }
            Gravity.BOTTOM -> {
                shapeDrawablePadding.top = elevationValue
                shapeDrawablePadding.bottom = elevationValue * 2
                yCoordinate = elevationValue / 3
            }
            else -> {
                shapeDrawablePadding.top = elevationValue
                shapeDrawablePadding.bottom = elevationValue * 2
                yCoordinate = elevationValue / 3
            }
        }
        
        val shapeDrawable = ShapeDrawable()
        shapeDrawable.setPadding(shapeDrawablePadding)
        
        shapeDrawable.paint.color = backgroundColorValue
        shapeDrawable.paint.setShadowLayer(
            cornerRadiusValue / 3f,
            0f,
            yCoordinate.toFloat(),
            shadowColorValue
        )
        
        shapeDrawable.paint
        
        shapeDrawable.shape = OvalShape()
        
        val drawable = LayerDrawable(arrayOf<Drawable>(shapeDrawable))
        drawable.setLayerInset(
            0,
            elevationValue,
            elevationValue * 2,
            elevationValue,
            elevationValue * 2
        )
        return drawable
    }
}