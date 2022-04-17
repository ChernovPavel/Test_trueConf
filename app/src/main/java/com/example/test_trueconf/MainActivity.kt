package com.example.test_trueconf

import android.animation.*
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.text)
        val container = findViewById<FrameLayout>(R.id.container)

        container.setOnTouchListener { _, event ->

            val bottom = container.bottom.toFloat() - textView.height.toFloat()
            val top = container.top.toFloat()

            textView.x = event.x
            textView.y = event.y

            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    when (Locale.getDefault().displayLanguage) {
                        "English" -> {
                            textView.setTextColor(Color.RED)
                        }
                        "русский" -> {
                            textView.setTextColor(Color.BLUE)
                        }
                    }
                }
            }

            val firstYDown = ObjectAnimator.ofFloat(
                textView,
                View.TRANSLATION_Y,
                textView.y,
                bottom
            )

            val yUp = ObjectAnimator.ofFloat(
                textView,
                View.TRANSLATION_Y,
                bottom,
                top
            )

            val nextYDown = ObjectAnimator.ofFloat(
                textView,
                View.TRANSLATION_Y,
                top,
                bottom
            )

            val firstSet = AnimatorSet().apply {
                startDelay = 2000L
                duration = 2000L
                interpolator = LinearInterpolator()
                playSequentially(firstYDown, yUp)
            }

            val nextSet = AnimatorSet().apply {
                duration = 2000L
                interpolator = LinearInterpolator()
                playSequentially(nextYDown, yUp)
            }

            firstSet.start()

            firstSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    nextSet.end()
                }

                override fun onAnimationEnd(p0: Animator?) {
                    nextSet.start()
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }
            })

            nextSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    nextSet.start()
                }

                override fun onAnimationCancel(p0: Animator?) {
                }

                override fun onAnimationRepeat(p0: Animator?) {
                }
            })
            true
        }
    }
}