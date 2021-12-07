package com.madrat.ovalexample

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import com.madrat.ovalexample.databinding.CustomViewOvalShadowedViewBinding

class OvalShadowedView: ConstraintLayout {
    private var binding: CustomViewOvalShadowedViewBinding? = null
    
    constructor(context: Context)
        : super(context){
        doOnInit(
            context,
            null,
            0,
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
            0,
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
            defStyleAttr,
            0
        )
    }
    
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ): super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        doOnInit(
            context,
            attrs,
            defStyleAttr,
            defStyleRes
        )
    }
    
    private fun doOnInit(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        binding = CustomViewOvalShadowedViewBinding.inflate(
            LayoutInflater.from(
                context,
            ),
            this,
            false
        )
        this.getDimensions { width, height ->
            addView(
                binding?.root,
                width,
                (width * 1.05).toInt()
            )
        }
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.OvalShadowedView,
            defStyleAttr,
            defStyleRes
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
                            android.R.color.darker_gray,
                            R.dimen.elevation,
                            Gravity.BOTTOM
                        )
                        backgroundImage.background = backgroundImageDrawable
                        if (isViewClickable) {
                            foregroundImage.setOnClickListener {
                                //finish()
                            }
                        } else {
                            foregroundImage.setOnClickListener(null)
                        }
                        foregroundImage.background = foregroundImageDrawable
                        rippleImage.foreground = getRippleAnimDrawable(
                            backgroundImage.background
                        )
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
    
    inline fun View.getDimensions(crossinline onDimensionsReady: (Int, Int) -> Unit) {
        lateinit var layoutListener: ViewTreeObserver.OnGlobalLayoutListener
        layoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            viewTreeObserver.removeOnGlobalLayoutListener(layoutListener)
            onDimensionsReady(width, height)
        }
        viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
    }
}