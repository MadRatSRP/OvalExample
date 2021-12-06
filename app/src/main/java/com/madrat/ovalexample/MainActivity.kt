package com.madrat.ovalexample

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import androidx.core.content.ContextCompat
import com.madrat.ovalexample.R
import android.view.Gravity
import android.view.View
import com.madrat.ovalexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        val foregroundDrawable = ContextCompat.getDrawable(
            this,
            R.drawable.ic_ellipse_21__1_
        )
        binding.rootLayout.background = ViewUtils.generateBackgroundWithShadow(
            this,
            R.color.white,
            R.dimen.radius_corner,
            android.R.color.darker_gray,
            R.dimen.elevation,
            Gravity.BOTTOM
        )
        binding.imageView.background = foregroundDrawable
        val isClickable = true
        if (isClickable) {
            binding.imageView.setOnClickListener {
                finish()
            }
        } else {
            binding.imageView.setOnClickListener(null)
        }
    }
    private fun getRippleAnimDrawable(
        drawable: Drawable
    ): RippleDrawable = RippleDrawable(
        ColorStateList.valueOf(
            this.getColor(R.color.white)
        ),
        null,
        drawable
    )
}