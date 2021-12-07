package com.madrat.ovalexample

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.madrat.ovalexample.databinding.CustomViewOvalShadowedViewBinding
import kotlin.math.roundToInt

class OvalShadowedView: ConstraintLayout {
    private var binding: CustomViewOvalShadowedViewBinding? = null
    
    constructor(context: Context)
        : super(context){
        doOnInit(
            context,
            null,
            0
        )
    }
    
    constructor(
        context: Context,
        attrs: AttributeSet?
    ): super(
        context,
        attrs
    ) {
        doOnInit(
            context,
            attrs,
            0
        )
    }
    
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        doOnInit(
            context,
            attrs,
            defStyleAttr
        )
    }
    
    private fun doOnInit(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) {
        binding = CustomViewOvalShadowedViewBinding.inflate(
            LayoutInflater.from(
                context,
            ),
            this,
            false
        )
        this.getDimensions { width, _ ->
            addView(
                binding?.root,
                width,
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    (width / 2.375).toFloat(),
                    resources.displayMetrics
                ).roundToInt()
            )
        }
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.OvalShadowedView,
            defStyleAttr,
            0
        ).apply {
            try {
                val backgroundImageDrawable = getDrawable(
                    R.styleable.OvalShadowedView_backgroundImage
                )
                val foregroundImageDrawable = getDrawable(
                    R.styleable.OvalShadowedView_foregroundImage
                )
                val isViewClickable = getBoolean(
                    R.styleable.OvalShadowedView_isClickable,
                    false
                )
                binding?.let {
                    with(it) {
                        root.background = ViewUtils.generateBackgroundWithShadow(
                            this@OvalShadowedView.context,
                            R.color.white,
                            R.dimen.radius_corner,
                            R.color.black_40,
                            R.dimen.elevation,
                            Gravity.BOTTOM
                        )
                        backgroundImage.background = backgroundImageDrawable
                        foregroundImage.background = foregroundImageDrawable
                        rippleImage.foreground = if (isViewClickable) {
                            getRippleAnimDrawable(
                                backgroundImage.background
                            )
                        } else {
                            null
                        }
                    }
                }
            } finally {
                recycle()
            }
        }
    }
    
    private fun getRippleAnimDrawable(
        drawable: Drawable
    ): RippleDrawable = RippleDrawable(
        ColorStateList.valueOf(
            this.context.getColor(R.color.white)
        ),
        null,
        drawable
    )
    
    private inline fun getDimensions(crossinline onDimensionsReady: (Int, Int) -> Unit) {
        viewTreeObserver.apply {
            addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    onDimensionsReady(
                        width,
                        height
                    )
                }
            })
        }
    }
}