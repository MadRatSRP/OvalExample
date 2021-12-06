package com.madrat.ovalexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.graphics.drawable.Drawable
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
        
        /*binding.imageView.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.darker_gray
            )
        );*/
        val foregroundDrawable = ContextCompat.getDrawable(
            this,
            R.drawable.ic_ellipse_21__1_
        )
        binding!!.rootLayout.background = ViewUtils.generateBackgroundWithShadow(
            this,
            R.color.white,
            R.dimen.radius_corner,
            android.R.color.darker_gray,
            R.dimen.elevation,
            Gravity.BOTTOM
        )
        binding?.imageView?.foreground = foregroundDrawable
    }
}